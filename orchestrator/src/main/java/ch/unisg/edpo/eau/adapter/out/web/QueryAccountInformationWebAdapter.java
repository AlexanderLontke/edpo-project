package ch.unisg.edpo.eau.adapter.out.web;

import ch.unisg.edpo.eau.application.port.out.QueryAccountInformationPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class QueryAccountInformationWebAdapter implements QueryAccountInformationPort {
    //TODO return Optional instead of empty string
    @Override
    public String retrieveAccountInformation(String accountId) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/Account/" + accountId))
                .headers("Content-Type", "application/json")
                .GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                //System.out.println(response.body());
                return response.body();
            }
            return "";
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
            return "";
        }
    }
}
