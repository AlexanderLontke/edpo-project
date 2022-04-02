package ch.unisg.edpo.eau;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Named
public class RetrieveAccounts implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/Accounts/today"))
                .headers("Content-Type", "application/json")
                .GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                //TODO handling of response should be improved
                String body = response.body().replaceAll("\\s+","");
                String[] accountNumbers = body.substring(1, body.length() - 1).split(",");
                delegateExecution.setVariable("accountNumberList", Arrays.asList(accountNumbers));
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}