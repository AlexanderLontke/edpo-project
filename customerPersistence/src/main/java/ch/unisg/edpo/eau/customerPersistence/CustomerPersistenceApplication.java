package ch.unisg.edpo.eau.customerPersistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = TaskListRepository.class)

public class CustomerPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPersistenceApplication.class, args);
	}

}
