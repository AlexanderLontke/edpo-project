package ch.unisg.paymentgateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;


@RestController
public class CreateTransactionController {
    @PostMapping(path = "/paymentTransaction", consumes = {Account.MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody Account account) {
        if(isValidTransaction(account)) {
            return new ResponseEntity<>("Successfully booked " + account.getAmount(), new HttpHeaders(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("There was an issue processing the transaction.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
    private Boolean isValidTransaction(Account account){
        Random random = new Random();
        int upperBound = 5;
        return random.nextInt(upperBound) != 1;
    }
}
