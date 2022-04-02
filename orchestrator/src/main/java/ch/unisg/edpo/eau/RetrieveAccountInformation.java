package ch.unisg.edpo.eau;

import ch.unisg.edpo.eau.domain.Account;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Named
public class RetrieveAccountInformation implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<String> accountNumbers;
        accountNumbers = (List) delegateExecution.getVariable("accountNumberList");

        List<String> serializedAccounts = new ArrayList<>();

        for (String accountNumber : accountNumbers) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/Account/" + accountNumber))
                    .headers("Content-Type", "application/json")
                    .GET().build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    //System.out.println(response.body());
                    serializedAccounts.add(response.body());
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        delegateExecution.setVariable("accountList", serializedAccounts);
    }
}