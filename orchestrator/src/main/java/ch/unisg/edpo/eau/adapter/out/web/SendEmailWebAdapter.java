package ch.unisg.edpo.eau.adapter.out.web;

import ch.unisg.edpo.eau.application.port.out.SendEmailPort;
import ch.unisg.edpo.eau.common.ConfigProperties;
import ch.unisg.edpo.eau.domain.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class SendEmailWebAdapter implements SendEmailPort {
    @Autowired
    ConfigProperties configProperties;

    @Override
    public void sendEmail(Email email) {
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(configProperties.getEmailServiceURL() + "/email"))
                    .POST(HttpRequest.BodyPublishers.ofString(Email.serialize(email)))
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println(response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
