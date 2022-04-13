package ch.unisg.edpo.eau.paymentgateway;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class BookingTransactionDTO {
    public static final String MEDIA_TYPE = "application/json";
    public enum TransactionStatusValues {
        OPEN, PROCESSED, FAILED
    }

    @Getter
    @Setter
    private String accountId;

    @Getter
    @Setter
    private String iban;

    @Getter @Setter
    private Double amount;

    @Getter @Setter
    private TransactionStatusValues Status;

    public BookingTransactionDTO() {  }

    public BookingTransactionDTO(String accountNumber, String iban, Double amount, TransactionStatusValues status) {
        this.accountId = accountNumber;
        this.iban = iban;
        this.amount = amount;
        this.Status = status;
    }

    public BookingTransactionDTO(String accountNumber, String iban, Double amount) {
        this.accountId = accountNumber;
        this.iban = iban;
        this.amount = amount;
        this.Status = TransactionStatusValues.OPEN;
    }

    public static String serialize(BookingTransactionDTO bookingTransaction) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(bookingTransaction);
    }

    public static BookingTransactionDTO deserialize(String transactionString) throws JsonProcessingException {
        JsonNode accountData = new ObjectMapper().readTree(transactionString);

        return new BookingTransactionDTO(
                accountData.get("accountId").textValue(),
                accountData.get("iban").textValue(),
                accountData.get("amount").doubleValue(),
                TransactionStatusValues.valueOf(accountData.get("status").textValue()));
    }
}
