package ch.unisg.edpo.eau.adapter.out.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class CustomerDTO {
    public static final String MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String customerID;

    @Getter
    @Setter
    private Double outstandingAmount;

    @Getter
    @Setter
    private String IBAN;

    @Getter
    @Setter
    private String billingCycle;

    @Getter
    private final String email;


    public CustomerDTO(String customerID, Double outstandingAmount, String IBAN, String billingCycle, String email) {
        this.customerID = customerID;
        this.outstandingAmount = outstandingAmount;
        this.IBAN = IBAN;
        this.billingCycle = billingCycle;
        this.email = email;
    }

    public static String serialize(CustomerDTO customerDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(customerDTO);
    }

    public static CustomerDTO deserialize(String customerString) throws JsonProcessingException {
        JsonNode customerData = new ObjectMapper().readTree(customerString);

        return new CustomerDTO(
                customerData.get("customerID").textValue(),
                customerData.get("outstandingAmount").doubleValue(),
                customerData.get("iban").textValue(),
                customerData.get("billingCycle").textValue(),
                customerData.get("email").textValue()
        );
    }
}

