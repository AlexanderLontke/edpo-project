package ch.unisg.customerdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class Account {
    public static final String MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String accountId;

    @Getter @Setter
    private Double amount;

    @Getter @Setter
    private String IBAN;

    @Getter @Setter
    private String billingCycle;

    public Account() {  }

    public Account(String accountNumber, Double amount, String iban, String cycle) {
        this.accountId = accountNumber;
        this.amount = amount;
        this.IBAN = iban;
        this.billingCycle = cycle;
    }

    public Account(String accountNumber) {
        this.accountId = accountNumber;
        this.amount = 420.69;
        this.IBAN = accountNumber + "-0815471108154711";
        this.billingCycle = "yearly";
    }

    public static String serialize(String accountNumber) throws JsonProcessingException {
        Account representation = new Account(accountNumber);
        return serialize(representation);
    }

    public static String serialize(Account account) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(account);
    }

    public static Account deserialize(String accountString) throws JsonProcessingException {
        JsonNode accountData = new ObjectMapper().readTree(accountString);

        return new Account(
                accountData.get("accountId").textValue(),
                accountData.get("amount").doubleValue(),
                accountData.get("iban").textValue(),
                accountData.get("billingCycle").textValue());
    }
}
