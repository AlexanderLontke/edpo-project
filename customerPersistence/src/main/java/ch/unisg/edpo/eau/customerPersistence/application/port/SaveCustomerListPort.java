package ch.unisg.edpo.eau.customerPersistence.application.port;

import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import ch.unisg.edpo.eau.customerPersistence.domain.CustomerList;

import java.util.List;

public interface SaveCustomerListPort {
    void saveCustomerList(CustomerList customerList);
}
