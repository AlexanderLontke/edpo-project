package ch.unisg.edpo.eau.onboardingGateway;

import ch.unisg.edpo.eau.onboardingGateway.application.adapter.messaging.dto.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@SpringBootApplication
public class OnboardingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardingGatewayApplication.class, args);
	}

	@Bean
	public Supplier<Message<?>> producer() {
		return () -> new Message<>("supplierEvent"," send via supplier");
	}
}
