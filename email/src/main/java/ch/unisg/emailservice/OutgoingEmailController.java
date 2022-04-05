package ch.unisg.emailservice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
public class OutgoingEmailController {
    @PostMapping(path = "/email", consumes = {Email.MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody Email email) {
        System.out.println("Successfully send email \"" + email.getTitle() + "\" to " + email.getRecipient());
        return new ResponseEntity<>("Successfully send email \"" + email.getTitle() + "\" to " + email.getRecipient(), new HttpHeaders(), HttpStatus.CREATED);
    }
}
