package ch.unisg.edpo.eau.eventstrom.serialization.json;

import ch.unisg.edpo.eau.eventstrom.serialization.EnergyMeterReading;
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

class EnergyMeterSerializer implements Serializer<EnergyMeterReading> {
  private Gson gson = new Gson();

  @Override
  public byte[] serialize(String topic, EnergyMeterReading tweet) {
    if (tweet == null) return null;
    return gson.toJson(tweet).getBytes(StandardCharsets.UTF_8);
  }
}
