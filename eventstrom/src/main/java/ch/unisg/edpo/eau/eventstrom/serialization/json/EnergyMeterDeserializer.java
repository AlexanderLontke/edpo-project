package ch.unisg.edpo.eau.eventstrom.serialization.json;

import ch.unisg.edpo.eau.eventstrom.serialization.EnergyMeterReading;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class EnergyMeterDeserializer implements Deserializer<EnergyMeterReading> {
  private Gson gson =
      new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

  @Override
  public EnergyMeterReading deserialize(String topic, byte[] bytes) {
    if (bytes == null) return null;
    return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), EnergyMeterReading.class);
  }
}
