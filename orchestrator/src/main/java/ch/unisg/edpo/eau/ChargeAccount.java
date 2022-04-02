package ch.unisg.edpo.eau;

import ch.unisg.edpo.eau.domain.Account;
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
import java.util.Random;

@Named
public class ChargeAccount implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accountString = (String) delegateExecution.getVariable("account");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/paymentTransaction/"))
                .POST(HttpRequest.BodyPublishers.ofString(accountString))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println(response.body());
            } else if (response.statusCode() == 400) {
                throw new BpmnError("TransactionError", "The Transaction could not be processed.");
            } else {
                throw new BpmnError("WrongHttpStatusError", "The transaction was answered with a wrong status code by the payment gateway.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new BpmnError("UnknownError", "There was an unknown error processing the transaction.");
        }
    }
}