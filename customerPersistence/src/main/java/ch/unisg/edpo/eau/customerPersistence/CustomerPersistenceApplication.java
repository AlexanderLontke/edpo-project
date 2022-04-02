package ch.unisg.edpo.eau.customerPersistence;

import ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb.CustomerListRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CustomerListRepository.class)
public class CustomerPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPersistenceApplication.class, args);
	}

}
