package ch.unisg.edpo.eau;

import ch.unisg.edpo.eau.domain.Account;
import ch.unisg.edpo.eau.domain.Email;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import javax.inject.Named;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Named
public class SendConfirmationEmail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accountString = (String) delegateExecution.getVariable("account");
        Account account = Account.deserialize(accountString);

        //TODO should be email not accountId
        Email email = new Email(account.getAccountId(), "Your Bill was cleared", "We were successful in booking your last invoice of " + account.getAmount() + " off the bank on file for your Account.");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8085/email"))
            .POST(HttpRequest.BodyPublishers.ofString(Email.serialize(email)))
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println(response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}