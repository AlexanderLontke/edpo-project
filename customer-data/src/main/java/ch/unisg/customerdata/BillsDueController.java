package ch.unisg.customerdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;


@RestController
public class BillsDueController {
    @GetMapping(path = "/Accounts/today")
    public ResponseEntity<String> addNumbers() {
        String[] accountNumbers = {"fancyAcc123", "businessAcc69", "firmAccount", "privatePerson0815", "Acc123", "Hello47", "poorGerman"};
        //String[] accountNumbers = {"fancyAcc123", "businessAcc69"};

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, Account.MEDIA_TYPE);

        return new ResponseEntity<>(Arrays.toString(accountNumbers), responseHeaders, HttpStatus.OK);
    }
}
