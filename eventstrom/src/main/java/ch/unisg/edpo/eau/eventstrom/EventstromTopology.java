package ch.unisg.edpo.eau.eventstrom;


import ch.unisg.edpo.eau.eventstrom.model.Billing;
import ch.unisg.edpo.eau.eventstrom.model.Customer;
import ch.unisg.edpo.eau.eventstrom.serdes.CustomerNoAvro;
import ch.unisg.edpo.eau.eventstrom.serdes.EnergyMeterNoAvro;
import ch.unisg.edpo.eau.eventstrom.model.CustomerAndEnergy;
import ch.unisg.edpo.eau.eventstrom.serdes.CustomerSerdes;
import ch.unisg.edpo.eau.eventstrom.serdes.EnergyMeterSerdes;
import com.mitchseymour.kafka.serialization.avro.AvroSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;


class EventstromTopology {

    public static Topology build() {
        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();

        // Serde<EnergyMeter> energyMeterSerde = AvroSerdes.get(EnergyMeter.class);
        // Serde<Customer> customerSerde = AvroSerdes.get(Customer.class);
        Serde<EnergyMeterNoAvro> energyMeterSerde = new EnergyMeterSerdes();
        Serde<CustomerNoAvro> customerSerde = new CustomerSerdes();
        // TODO ensure co-partitioning of un-keyed energy events
        KStream<String, EnergyMeterNoAvro> consumerStream =
                builder.stream("energy-consumer", Consumed.with(Serdes.String(), energyMeterSerde));
        KStream<String, EnergyMeterNoAvro> producerStream =
                builder.stream("energy-producer", Consumed.with(Serdes.String(), energyMeterSerde))
                        .mapValues((v) -> {
                            // Invert energy coming from producers as it should be treated
                            // as negative energy consumption
                            v.setDelta_e(v.getDelta_e() * -1);
                            return v;
                        });

        // Merge producer and consumer reading streams
        KStream<String, EnergyMeterNoAvro> energyStream =
                consumerStream
                        .merge(producerStream)
                        .selectKey((k, v) -> v.getCustomer_id());

        energyStream.print(Printed.<String, EnergyMeterNoAvro>toSysOut().withLabel("merged-and-keyed"));

        // Create shared customer table
        KTable<String, CustomerNoAvro> customers =
                builder.table("customers", Consumed.with(Serdes.String(), customerSerde));

        customers.toStream().print(Printed.<String, CustomerNoAvro>toSysOut().withLabel("customers"));

        // Join params for energy events -> customers
        Joined<String, EnergyMeterNoAvro, CustomerNoAvro> energyJoinParams =
                Joined.with(Serdes.String(), energyMeterSerde, customerSerde);

        // join energy events -> customers
        ValueJoiner<EnergyMeterNoAvro, CustomerNoAvro, CustomerAndEnergy> energyCustomerJoiner =
                (energyMeter, customer) -> {
                    System.out.println("energymeter:");
                    System.out.println(energyMeter.toString());
                    System.out.println("customer:");
                    System.out.println(customer.toString());

                    return CustomerAndEnergy.newBuilder()
                            .setMessageId(energyMeter.getMessage_id())
                            .setMessageType(energyMeter.getMessage_type())
                            .setTimeStart(energyMeter.getTime_start())
                            .setTimeEnd(energyMeter.getTime_end())
                            .setDeltaE(energyMeter.getDelta_e())
                            .setDeviceId(energyMeter.getDevice_id())
                            .setCustomerId(customer.getCustomer_id())
                            .setCustomerName(customer.getCustomer_name())
                            .setCustomerPostalCode(customer.getCustomer_postal_code())
                            .build();
                };
        KStream<String, CustomerAndEnergy> energyWithCustomers =
                energyStream.join(customers, energyCustomerJoiner, energyJoinParams);

        energyWithCustomers.print(Printed.<String, CustomerAndEnergy>toSysOut().withLabel("customer-and-energy"));

        // TODO: Use custom aggregate function to aggregate energy consumed
        // The initial value of our aggregation will be a new Billing instances
        Initializer<Billing> billingInitializer = Billing::new;

        // The logic for aggregating high scores is implemented in the HighScores.add method
        Aggregator<String, CustomerAndEnergy, Billing> billingAggregator = new Aggregator<String, CustomerAndEnergy, Billing>() {
            @Override
            public Billing apply(String s, CustomerAndEnergy customerAndEnergy, Billing billing) {
                System.out.println("Billing before:");
                System.out.println(billing);
                billing.setCustomerId(customerAndEnergy.getCustomerId());
                billing.setTotalEnergyAmount(billing.getTotalEnergyAmount() + customerAndEnergy.getDeltaE());
                System.out.println("Billing after:");
                System.out.println(billing);
                return billing;
            }
        };

        // turn energy events into energy per month
        TimeWindows tumblingWindow =
                TimeWindows.of(Duration.ofDays(30));

        // Create monthly billing events by windowing
        // TODO: Agree on billing events avro schema with Erik
        //KTable<Windowed<String>, Billing> windowedCustomerEnergy =
        energyWithCustomers
                .groupByKey(
                        //(key, customerAndEnergy) -> customerAndEnergy.getCustomerId().toString(),
                        Grouped.with(
                                "grouped-energy-by-customer",
                                Serdes.String(),
                                AvroSerdes.get(CustomerAndEnergy.class)
                        ))
                .windowedBy(tumblingWindow)
                .aggregate(billingInitializer, billingAggregator,
                        Materialized.<String, Billing, WindowStore<Bytes,byte[]>>as("windowed-billing")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(AvroSerdes.get(Billing.class))
                )
                .toStream().print(Printed.<Windowed<String>, Billing>toSysOut().withLabel("billing"));
        //.suppress(Suppressed.untilWindowCloses(BufferConfig.unbounded().shutDownWhenFull()));
        //windowedCustomerEnergy.toStream().print(Printed.<Windowed<String>, Billing>toSysOut().withLabel("billing"));
        return builder.build();
    }
}
