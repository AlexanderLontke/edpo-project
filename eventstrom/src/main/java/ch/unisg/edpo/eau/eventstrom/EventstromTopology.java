package ch.unisg.edpo.eau.eventstrom;


import ch.unisg.edpo.eau.eventstrom.model.Customer;
import ch.unisg.edpo.eau.eventstrom.model.EnergyMeter;
import ch.unisg.edpo.eau.eventstrom.model.CustomerAndEnergy;
import com.mitchseymour.kafka.serialization.avro.AvroSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

class EventstromTopology {

    public static Topology build() {
        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();

        Serde<EnergyMeter> energyMeterSerde = AvroSerdes.get(EnergyMeter.class);
        Serde<Customer> customerSerde = AvroSerdes.get(Customer.class);

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

        KGroupedStream<String, CustomerAndEnergy> grouped = energyWithCustomers.groupBy(
                (key, customerAndEnergy) -> customerAndEnergy.getCustomerId().toString(),
                Grouped.with(
                        "grouped-customer-and-energy",
                        Serdes.String(),
                        AvroSerdes.get(CustomerAndEnergy.class)
                )
        );
        return builder.build();
    }
}
