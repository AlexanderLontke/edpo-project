package ch.unisg.edpo.eau.onboardingGateway.adapter.messaging;

import ch.unisg.edpo.eau.onboardingGateway.adapter.messaging.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private StreamBridge streamBridge;

    public void send(String topic, Message<?> message) {
        streamBridge.send(
                topic, message
        );
    }
}
