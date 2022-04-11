package ch.unisg.edpo.eau.customerPersistence.adapter.web.http.out;

import ch.unisg.edpo.eau.customerPersistence.adapter.web.http.dto.EmailDTO;
import ch.unisg.edpo.eau.customerPersistence.application.port.SendEmailPort;
import ch.unisg.edpo.eau.customerPersistence.common.ConfigProperties;
import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class SendEmailAdapter implements SendEmailPort {
    @Autowired
    ConfigProperties configProperties;

    @Override
    public void sendEmail(Customer customer) {
        String content = "Customer" + customer.getName() + "is ";
        if (!customer.isApproved()) {
            content += "not ";
        }
        content += "approved.";

        EmailDTO emailDTO = new EmailDTO(
                customer.getEmail(),
                "Registration Email",
                content
        );
        String requestBody;
        try {
            requestBody = EmailDTO.serialize(emailDTO);
            URI emailURI = URI.create(configProperties.getEmailServiceURL() + "/email");
            HttpClient httpClient = HttpClient.newHttpClient();
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .header(HttpHeaders.CONTENT_TYPE, EmailDTO.MEDIA_TYPE)
                        .uri(emailURI)
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() / 100 != 2) {
                    System.err.printf(
                            "Got wrong response code %d when sending %s-request to %s with body:%n%s%n" +
                                    "ResponseBody:%n%s%n",
                            response.statusCode(),
                            request.uri().toString(),
                            request.method(),
                            requestBody,
                            response.body()
                    );
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            System.err.println("error serializing email DTO.");
            e.printStackTrace();
        }
    }
}
