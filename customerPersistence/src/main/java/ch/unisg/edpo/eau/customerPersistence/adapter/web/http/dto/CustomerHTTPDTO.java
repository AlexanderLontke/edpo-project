package ch.unisg.edpo.eau.customerPersistence.adapter.web.http.dto;

import ch.unisg.edpo.eau.customerPersistence.domain.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class CustomerHTTPDTO {
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

    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private final boolean approved;
    private final String email;


    public CustomerHTTPDTO(String customerID, Double outstandingAmount, String IBAN, String billingCycle, String name, String street, int houseNumber, int postalCode, boolean approved, String email) {
        this.customerID = customerID;
        this.outstandingAmount = outstandingAmount;
        this.IBAN = IBAN;
        this.billingCycle = billingCycle;
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.approved = approved;
        this.email = email;
    }

    public static String serialize(Customer customer) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(customer);
    }

    public static CustomerHTTPDTO deserialize(String customerString) throws JsonProcessingException {
        JsonNode customerData = new ObjectMapper().readTree(customerString);

        return new CustomerHTTPDTO(
                customerData.get("customerID").textValue(),
                customerData.get("outstandingAmount").doubleValue(),
                customerData.get("iban").textValue(),
                customerData.get("billingCycle").textValue(),
                customerData.get("name").textValue(),
                customerData.get("street").textValue(),
                customerData.get("houseNumber").intValue(),
                customerData.get("postalCode").intValue(),
                customerData.get("approved").booleanValue(),
                customerData.get("email").textValue()
        );
    }
}

