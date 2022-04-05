package ch.unisg.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication paymentGateway = new SpringApplication(EmailServiceApplication.class);
		paymentGateway.run(args);
	}

}
