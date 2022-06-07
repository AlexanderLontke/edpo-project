package ch.unisg.edpo.eau.eventstrom;

import ch.unisg.edpo.eau.eventstrom.model.Billing;
import com.mitchseymour.kafka.serialization.avro.AvroSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.state.HostInfo;

import java.util.Properties;

class App {
  public static void main(String[] args) {
    Topology topology = EventstromTopology.build();

    // set the required properties for running Kafka Streams
    Properties config = new Properties();
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
    config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
    config.put(StreamsConfig.STATE_CLEANUP_DELAY_MS_CONFIG, "6");

    // config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
    // config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,  AvroSerdes.get(Billing.class));
    // config.put("schema.registry.url", "http://localhost:8081");

    // build the topology and start streaming!
    KafkaStreams streams = new KafkaStreams(topology, config);

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    // clean up local state since many of the tutorials write to the same location
    // you should run this sparingly in production since it will force the state
    // store to be rebuilt on start up
    streams.cleanUp();


    System.out.println("Starting Energy streams");
    streams.start();

    // start the REST service
    HostInfo hostInfo = new HostInfo("localhost", 9001);
    RestService service = new RestService(hostInfo, streams);
    service.start();
  }
}
