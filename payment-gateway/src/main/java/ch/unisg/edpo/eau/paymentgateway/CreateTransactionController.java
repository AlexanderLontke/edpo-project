package ch.unisg.edpo.eau.paymentgateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
public class CreateTransactionController {
    @PostMapping(path = "/paymentTransaction", consumes = {BookingTransactionDTO.MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody BookingTransactionDTO bookingTransaction) throws JsonProcessingException {

        if(isValidTransaction(bookingTransaction)) {
            bookingTransaction.setStatus(BookingTransactionDTO.TransactionStatusValues.PROCESSED);
            return new ResponseEntity<>(BookingTransactionDTO.serialize(bookingTransaction), new HttpHeaders(), HttpStatus.CREATED);
        } else {
            bookingTransaction.setStatus(BookingTransactionDTO.TransactionStatusValues.FAILED);
            return new ResponseEntity<>(BookingTransactionDTO.serialize(bookingTransaction), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
    private Boolean isValidTransaction(BookingTransactionDTO bookingTransaction){
        //can be used here for validation in future extensions
        Random random = new Random();
        int upperBound = 5;
        return random.nextInt(upperBound) != 1;
    }
}
