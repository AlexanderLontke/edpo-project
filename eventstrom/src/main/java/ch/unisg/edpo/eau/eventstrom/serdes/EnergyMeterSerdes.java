package ch.unisg.edpo.eau.eventstrom.serdes;

import ch.unisg.edpo.eau.eventstrom.model.Customer;
import ch.unisg.edpo.eau.eventstrom.model.EnergyMeter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class EnergyMeterSerdes implements Serde<EnergyMeterNoAvro> {

    @Override
    public Serializer<EnergyMeterNoAvro> serializer() {
        return new Serializer<EnergyMeterNoAvro>() {
            private final Gson gson = new Gson();

            @Override
            public byte[] serialize(String topic, EnergyMeterNoAvro meter) {
                if (meter == null) return null;
                return gson.toJson(meter).getBytes(StandardCharsets.UTF_8);
            }
        };
    }

    @Override
    public Deserializer<EnergyMeterNoAvro> deserializer() {
        return new Deserializer<EnergyMeterNoAvro>() {
            private final Gson gson =
                    new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            @Override
            public EnergyMeterNoAvro deserialize(String s, byte[] bytes) {
                if (bytes == null) return null;
                return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), EnergyMeterNoAvro.class);
            }
        };
    }
}
