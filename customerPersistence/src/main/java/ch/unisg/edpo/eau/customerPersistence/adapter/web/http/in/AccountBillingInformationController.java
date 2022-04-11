package ch.unisg.edpo.eau.customerPersistence.adapter.web.http.in;

import ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb.CustomerListRepository;
import ch.unisg.edpo.eau.customerPersistence.adapter.web.http.dto.CustomerHTTPDTO;
import ch.unisg.edpo.eau.customerPersistence.application.port.LoadCustomerListPort;
import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import ch.unisg.edpo.eau.customerPersistence.domain.CustomerList;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class AccountBillingInformationController {
    @Autowired
    LoadCustomerListPort loadCustomerListPort;

    @GetMapping(path = "/Customer/{customerID}")
    public ResponseEntity<String> addNumbers(@PathVariable("customerID") String accountId) {
        try {
            CustomerList customerList = loadCustomerListPort.loadCustomerListById("");
            Customer customer = customerList.getCustomerList().stream()
                            .filter(x -> x.getCustomerID()
                                            .equals(accountId)).collect(Collectors.toList()).get(0);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, CustomerHTTPDTO.MEDIA_TYPE);

            return new ResponseEntity<>(CustomerHTTPDTO.serialize(customer), responseHeaders, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
