package ch.unisg.edpo.eau.eventstrom.serialization.json;

import ch.unisg.edpo.eau.eventstrom.serialization.EnergyMeterReading;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class EnergyMeterSerdes implements Serde<EnergyMeterReading> {

  @Override
  public Serializer<EnergyMeterReading> serializer() {
    return new EnergyMeterSerializer();
  }

  @Override
  public Deserializer<EnergyMeterReading> deserializer() {
    return new EnergyMeterDeserializer();
  }
}
