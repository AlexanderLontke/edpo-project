package ch.unisg.edpo.eau.customerApproval.adapter.messaging;

import ch.unisg.edpo.eau.customerApproval.adapter.messaging.dto.CustomerDTO;
import ch.unisg.edpo.eau.customerApproval.adapter.messaging.dto.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerApproval {
    @Bean
    public Function<Message<CustomerDTO>, Message<CustomerDTO>> approveCustomer(){
        return (message) -> {
            CustomerDTO customer = message.getData();
            if (customer.getPostalCode() >= 9000 && customer.getPostalCode() < 10000){
                customer.setApproved(true);
            }
            System.out.println("processed customer");
            return new Message<>("newProcessedCustomer", customer);
        };
    }
}
