package ch.unisg.edpo.eau.adapter.out.web;

import ch.unisg.edpo.eau.application.port.out.QueryAccruedAccountsPort;
import ch.unisg.edpo.eau.common.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
@Primary
public class QueryAccruedAccountsWebAdapter implements QueryAccruedAccountsPort {
    @Autowired
    ConfigProperties configProperties;

    @Override
    public Optional<String> retrieveAccountsDueToday() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configProperties.getCustomerPersistenceURL() + "/Customers/today"))
                .headers("Content-Type", "application/json")
                .GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return Optional.of(response.body());
            }
            return Optional.empty();
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
            return Optional.empty();
        }
    }
}
