package ch.unisg.edpo.eau.customerPersistence.adapter.web.http.in;

import ch.unisg.edpo.eau.customerPersistence.adapter.web.http.dto.CustomerHTTPDTO;
import ch.unisg.edpo.eau.customerPersistence.application.port.LoadCustomerListPort;
import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
public class BillsDueController {
    @Autowired
    LoadCustomerListPort loadCustomerListPort;
    @GetMapping(path = "/Customers/today")
    public ResponseEntity<String> addNumbers() {
        Object[] accountNumbers = loadCustomerListPort.
                loadCustomerListById("").
                getCustomerList().
                stream().
                filter(Customer::isApproved).
                map(Customer::getCustomerID).toArray();
        //System.out.println(Arrays.toString(accountNumbers));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, CustomerHTTPDTO.MEDIA_TYPE);

        return new ResponseEntity<>(Arrays.toString(accountNumbers), responseHeaders, HttpStatus.OK);
    }
}
