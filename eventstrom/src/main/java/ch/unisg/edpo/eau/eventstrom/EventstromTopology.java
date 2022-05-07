package ch.unisg.edpo.eau.eventstrom;


import ch.unisg.edpo.eau.eventstrom.model.Customer;
import ch.unisg.edpo.eau.eventstrom.model.EnergyMeter;
import ch.unisg.edpo.eau.eventstrom.model.join.CustomerAndEnergy;
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

        KStream<byte[], EnergyMeter> consumerStream =
                builder.stream("energyconsumer", Consumed.with(Serdes.ByteArray(), energyMeterSerde));
        KStream<byte[], EnergyMeter> producerStream =
                builder.stream("energyproducer", Consumed.with(Serdes.ByteArray(), energyMeterSerde));

        // Merge producer and consumer reading streams
        KStream<byte[], EnergyMeter> energyStream = consumerStream.merge(producerStream);

        // Create shared customer table
        KTable<byte[], Customer> customers =
                builder.table("customers", Consumed.with(Serdes.ByteArray(), customerSerde));

        // Join params for energy events -> customers
        Joined<byte[], EnergyMeter, Customer> energyJoinParams =
                Joined.with(Serdes.ByteArray(), energyMeterSerde, customerSerde);

        // join energy events -> customers
        ValueJoiner<EnergyMeter, Customer, CustomerAndEnergy> energyCustomerJoiner =
                CustomerAndEnergy::new;
        KStream<byte[], CustomerAndEnergy> energyWithCustomers =
                energyStream.join(customers, energyCustomerJoiner, energyJoinParams);

        KGroupedStream<String, CustomerAndEnergy> grouped = energyWithCustomers.groupBy(
                (key, value) -> value.getCustomer_id().toString()
        );

        return builder.build();
    }
}
