package ch.unisg.edpo.eau.onboardingGateway.application.adapter.http.in;

import ch.unisg.edpo.eau.onboardingGateway.application.adapter.http.dto.CustomerDTO;
import ch.unisg.edpo.eau.onboardingGateway.application.adapter.messaging.MessageSender;
import ch.unisg.edpo.eau.onboardingGateway.application.adapter.messaging.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiveNewCustomerAdapter {

    @Autowired
    public MessageSender messageSender;

    @PostMapping(value = "/customer")
    public ResponseEntity<String> receiveNewCustomerEvent(@RequestBody CustomerDTO customer){
        System.out.println("Customer received");
        messageSender.send(
                "new-customer-event", new Message<>("ReceiveNewCustomerEvent", customer.toEntity())
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
