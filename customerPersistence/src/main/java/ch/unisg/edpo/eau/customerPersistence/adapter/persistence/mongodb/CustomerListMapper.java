package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import ch.unisg.edpo.eau.customerPersistence.domain.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerListMapper {
    @Autowired
    CustomerMapper customerMapper;

    CustomerList toEntity(MongoCustomerListDocument mongoCustomerListDocument) {
        return new CustomerList(
                mongoCustomerListDocument.getCustomerListID(),
                mongoCustomerListDocument.getListOfCustomers().stream().map(customerMapper::toEntity).collect(Collectors.toList())
        );


    }

    MongoCustomerListDocument fromEntity(CustomerList customerList) {
        return new MongoCustomerListDocument(
                customerList.getId(),
                customerList.getCustomerList().stream().map(customerMapper::fromEntity).collect(Collectors.toList())
        );
    }
}
