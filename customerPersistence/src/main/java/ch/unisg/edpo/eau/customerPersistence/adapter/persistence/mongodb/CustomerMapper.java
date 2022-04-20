package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(MongoCustomerDocument mongoCustomerDocument) {
        return new Customer(
                mongoCustomerDocument.getCustomerID(),
                mongoCustomerDocument.getIBAN(),
                mongoCustomerDocument.getBillingCycle(),
                mongoCustomerDocument.getOutstandingAmount(),
                mongoCustomerDocument.getName(),
                mongoCustomerDocument.getStreet(),
                mongoCustomerDocument.getHouseNumber(),
                mongoCustomerDocument.getPostalCode(),
                mongoCustomerDocument.isApproved(),
                mongoCustomerDocument.getEmail()
        );
    }

    public MongoCustomerDocument fromEntity(Customer customer) {
        return new MongoCustomerDocument(
                customer.getName(),
                customer.getStreet(),
                customer.getHouseNumber(),
                customer.getPostalCode(),
                customer.isApproved(),
                customer.getCustomerID(),
                customer.getIBAN(),
                customer.getBillingCycle(),
                customer.getOutstandingAmount(),
                customer.getEmail()
        );
    }
}
