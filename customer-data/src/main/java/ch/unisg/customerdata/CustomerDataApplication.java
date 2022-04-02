package ch.unisg.customerdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerDataApplication {

	public static void main(String[] args) {
		SpringApplication customerData = new SpringApplication(CustomerDataApplication.class);
		customerData.run(args);
	}

}
