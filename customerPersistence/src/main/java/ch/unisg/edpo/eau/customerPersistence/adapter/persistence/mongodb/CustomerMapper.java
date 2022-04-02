package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(MongoCustomerDocument mongoCustomerDocument) {
        return new Customer(
                mongoCustomerDocument.getName(),
                mongoCustomerDocument.getStreet(),
                mongoCustomerDocument.getHouseNumber(),
                mongoCustomerDocument.getPostalCode(),
                mongoCustomerDocument.isApproved()
        );
    }

    public MongoCustomerDocument fromEntity(Customer customer) {
        return new MongoCustomerDocument(
                customer.getName(),
                customer.getStreet(),
                customer.getHouseNumber(),
                customer.getPostalCode(),
                customer.isApproved()
        );
    }
}
