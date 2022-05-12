package ch.unisg.edpo.eau.eventstrom;

import ch.unisg.edpo.eau.eventstrom.model.EnergyMeter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.Instant;

public class EventTimeStampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        // TODO discuss timestamps of our events since we have defined intervals (time_start, time_end)
        EnergyMeter record = (EnergyMeter) consumerRecord.value();
        if (record != null && record.getTimeStart() != null) {
            String timestamp = record.getTimeStart().toString();
            // System.out.println("Extracting timestamp: " + timestamp);
            return Instant.parse(timestamp).toEpochMilli();
        }
        // fallback to stream time
        return l;
    }
}
