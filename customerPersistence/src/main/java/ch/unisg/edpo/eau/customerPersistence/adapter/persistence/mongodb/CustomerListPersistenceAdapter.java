package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import ch.unisg.edpo.eau.customerPersistence.application.port.LoadCustomerListPort;
import ch.unisg.edpo.eau.customerPersistence.application.port.SaveCustomerListPort;
import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import ch.unisg.edpo.eau.customerPersistence.domain.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerListPersistenceAdapter implements SaveCustomerListPort, LoadCustomerListPort {
    @Autowired
    private CustomerListMapper customerListMapper;

    @Autowired
    private CustomerListRepository customerListRepository;

    @Override
    public CustomerList loadCustomerListById(String id) {
        MongoCustomerListDocument mongoCustomerListDocument;
        if (id.equals("")){
            List<MongoCustomerListDocument> allLists = customerListRepository.findAll();
            if (allLists.size() > 0) {
                mongoCustomerListDocument = allLists.get(0);
            } else {
                mongoCustomerListDocument = null;
            }
        }else{
            mongoCustomerListDocument = customerListRepository.findByCustomerListID(id);
        }
        if (mongoCustomerListDocument == null) {
            return new CustomerList(
                    UUID.randomUUID().toString(), new ArrayList<>()
            );
        }
        return customerListMapper.toEntity(mongoCustomerListDocument);
    }

    @Override
    public void saveCustomerList(CustomerList customerList) {
        customerListRepository.save(customerListMapper.fromEntity(customerList));
    }
}
