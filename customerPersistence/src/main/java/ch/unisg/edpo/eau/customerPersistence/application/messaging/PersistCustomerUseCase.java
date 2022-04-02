package ch.unisg.edpo.eau.customerPersistence.application.messaging;

import ch.unisg.edpo.eau.customerPersistence.application.messaging.dto.CustomerDTO;
import ch.unisg.edpo.eau.customerPersistence.application.messaging.dto.Message;
import ch.unisg.edpo.eau.customerPersistence.application.port.LoadCustomerListPort;
import ch.unisg.edpo.eau.customerPersistence.application.port.SaveCustomerListPort;
import ch.unisg.edpo.eau.customerPersistence.application.port.SendEmailPort;
import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import ch.unisg.edpo.eau.customerPersistence.domain.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PersistCustomerUseCase {
    @Autowired
    private SendEmailPort sendEmailPort;

    @Autowired
    private LoadCustomerListPort loadCustomerListPort;

    @Autowired
    private SaveCustomerListPort saveCustomerListPort;

    @Bean
    public Function<Message<CustomerDTO>, Message<CustomerDTO>> persistCustomer() {
        return customerDTOMessage -> {
            CustomerDTO customerDTO = customerDTOMessage.getData();
            Customer customer = customerDTO.toEntity();
            CustomerList customerList = loadCustomerListPort.loadCustomerList();
            customerList.getCustomerList().add(customer);
            saveCustomerListPort.saveCustomerList(customerList);
            sendEmailPort.sendEmail(customer);
            return new Message<>("FinishedCustomer", customerDTO);
        };
    }
}
