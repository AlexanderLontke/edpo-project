package ch.unisg.edpo.eau.eventstrom;


import ch.unisg.edpo.eau.eventstrom.model.Billing;
import ch.unisg.edpo.eau.eventstrom.model.Customer;
import ch.unisg.edpo.eau.eventstrom.model.EnergyMeter;
import ch.unisg.edpo.eau.eventstrom.model.CustomerAndEnergy;
import com.mitchseymour.kafka.serialization.avro.AvroSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.Suppressed.BufferConfig;
import org.apache.kafka.streams.kstream.internals.KStreamWindowAggregate;

import java.time.Duration;


class EventstromTopology {

    public static Topology build() {
        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();

        Serde<EnergyMeter> energyMeterSerde = AvroSerdes.get(EnergyMeter.class);
        Serde<Customer> customerSerde = AvroSerdes.get(Customer.class);
        // TODO ensure co-partitioning of un-keyed energy events
        KStream<String, EnergyMeter> consumerStream =
                builder.stream("energy-consumer", Consumed.with(Serdes.String(), energyMeterSerde));
        KStream<String, EnergyMeter> producerStream =
                builder.stream("energy-producer", Consumed.with(Serdes.String(), energyMeterSerde))
                        .mapValues((v) -> {
                            // Invert energy coming from producers as it should be treated
                            // as negative energy consumption
                            v.setDeltaE(v.getDeltaE() * -1);
                            return v;
                        });

        // Merge producer and consumer reading streams
        KStream<String, EnergyMeter> energyStream =
                consumerStream
                        .merge(producerStream)
                        .selectKey((k,v) -> v.getCustomerId().toString());

        // Create shared customer table
        KTable<String, Customer> customers =
                builder.table("customers", Consumed.with(Serdes.String(), customerSerde));

        // Join params for energy events -> customers
        Joined<String, EnergyMeter, Customer> energyJoinParams =
                Joined.with(Serdes.String(), energyMeterSerde, customerSerde);

        // join energy events -> customers
        ValueJoiner<EnergyMeter, Customer, CustomerAndEnergy> energyCustomerJoiner =
                (energyMeter, customer) -> CustomerAndEnergy.newBuilder()
                        .setMessageId(energyMeter.getMessageId())
                        .setMessageType(energyMeter.getMessageType())
                        .setTimeStart(energyMeter.getTimeStart())
                        .setTimeEnd(energyMeter.getTimeEnd())
                        .setDeltaE(energyMeter.getDeltaE())
                        .setDeviceId(energyMeter.getDeviceId())
                        .setCustomerId(customer.getCustomerId())
                        .setCustomerName(customer.getCustomerName())
                        .setCustomerPostalCode(customer.getCustomerPostalCode())
                        .build();
        KStream<String, CustomerAndEnergy> energyWithCustomers =
                energyStream.join(customers, energyCustomerJoiner, energyJoinParams);

        KGroupedStream<String, CustomerAndEnergy> groupedEnergyByCustomer = energyWithCustomers.groupBy(
                // TODO do we need to specify the key explicitly?
                (key, customerAndEnergy) -> customerAndEnergy.getCustomerId().toString(),
                Grouped.with(
                        "grouped-energy-by-customer",
                        Serdes.String(),
                        AvroSerdes.get(CustomerAndEnergy.class)
                )
        );

        // turn energy events into energy per month
        TimeWindows tumblingWindow =
                TimeWindows.of(Duration.ofDays(30)).grace(Duration.ofHours(1));

        // TODO: Use custom aggregate function to aggregate energy consumed
        // The initial value of our aggregation will be a new Billing instances
        Initializer<Billing> billingInitializer = Billing::new;

        // The logic for aggregating high scores is implemented in the HighScores.add method
        Aggregator<String, CustomerAndEnergy, Billing> billingAggregator = new Aggregator<String, CustomerAndEnergy, Billing>() {
            @Override
            public Billing apply(String s, CustomerAndEnergy customerAndEnergy, Billing billing) {

                return Billing.newBuilder()
                        .setCustomerId(customerAndEnergy.getCustomerId())
                        .setTotalEnergyAmount(billing.getTotalEnergyAmount() + customerAndEnergy.getDeltaE())
                        .build();
            }
        };

        // Create monthly billing events by windowing
        // TODO: Agree on billing events avro schema with Erik
        KTable<Windowed<String>, Billing> windowedCustomerEnergy =
                groupedEnergyByCustomer
                        .windowedBy(tumblingWindow)
                        .aggregate(billingInitializer, billingAggregator)
                        .suppress(Suppressed.untilWindowCloses(BufferConfig.unbounded().shutDownWhenFull()));
        return builder.build();
    }
}
