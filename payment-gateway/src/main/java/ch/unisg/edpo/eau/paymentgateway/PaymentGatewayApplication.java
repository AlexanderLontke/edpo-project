package ch.unisg.edpo.eau.paymentgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentGatewayApplication {

	public static void main(String[] args) {
		SpringApplication paymentGateway = new SpringApplication(PaymentGatewayApplication.class);
		paymentGateway.run(args);
	}

}
