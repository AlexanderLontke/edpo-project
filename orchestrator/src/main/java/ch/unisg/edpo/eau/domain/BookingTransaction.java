package ch.unisg.edpo.eau.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class BookingTransaction {
    public static final String MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String accountId;

    @Getter @Setter
    private Double amount;

    @Getter @Setter
    private String Status;

    public BookingTransaction() {  }

    public BookingTransaction(String accountNumber, Double amount, String status) {
        this.accountId = accountNumber;
        this.amount = amount;
        this.Status = status;
    }

    public static String serialize(BookingTransaction bookingTransaction) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(bookingTransaction);
    }

    public static BookingTransaction deserialize(String transactionString) throws JsonProcessingException {
        JsonNode accountData = new ObjectMapper().readTree(transactionString);

        return new BookingTransaction(
                accountData.get("accountId").textValue(),
                accountData.get("amount").doubleValue(),
                accountData.get("status").textValue());
    }
}
