package ch.unisg.edpo.eau.onboardingGateway.application.adapter.messaging;

import ch.unisg.edpo.eau.onboardingGateway.application.adapter.messaging.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(String topic, Message<?> message) {
        streamBridge.send(
                topic, message
        );
    }
}
