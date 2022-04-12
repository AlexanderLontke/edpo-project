package ch.unisg.edpo.eau.adapter.out.web;

import ch.unisg.edpo.eau.application.port.out.AccountChargePort;
import ch.unisg.edpo.eau.common.ConfigProperties;
import ch.unisg.edpo.eau.adapter.out.web.dto.BookingTransactionDTO;
import org.camunda.bpm.engine.delegate.BpmnError;
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
public class AccountChargeWebAdapter implements AccountChargePort {
    @Autowired
    ConfigProperties configProperties;

    @Override
    public void processTransaction(BookingTransactionDTO bookingTransaction) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(configProperties.getPaymentGatewayURL() + "/paymentTransaction/"))
                    .POST(HttpRequest.BodyPublishers.ofString(BookingTransactionDTO.serialize(bookingTransaction)))
                    .header(HttpHeaders.CONTENT_TYPE, BookingTransactionDTO.MEDIA_TYPE)
                    .build();
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
