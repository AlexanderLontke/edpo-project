package ch.unisg.edpo.eau.eventstrom.serdes;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class CustomerSerdes implements Serde<CustomerNoAvro> {

    @Override
    public Serializer<CustomerNoAvro> serializer() {
        return new Serializer<CustomerNoAvro>() {
            private final Gson gson = new Gson();

            @Override
            public byte[] serialize(String topic, CustomerNoAvro customer) {
                if (customer == null) return null;
                return gson.toJson(customer).getBytes(StandardCharsets.UTF_8);
            }
        };
    }

    @Override
    public Deserializer<CustomerNoAvro> deserializer() {
        return new Deserializer<CustomerNoAvro>() {
            private final Gson gson =
                    new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            @Override
            public CustomerNoAvro deserialize(String s, byte[] bytes) {
                if (bytes == null) return null;
                return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), CustomerNoAvro.class);
            }
        };
    }
}
