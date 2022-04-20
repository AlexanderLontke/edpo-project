package ch.unisg.edpo.eau.customerApproval.adapter.messaging.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private boolean approved;

    private final String customerID;
    private final String IBAN;
    private final String billingCycle;
    private final Double outstandingAmount;
    private final String email;

    @JsonCreator
    public CustomerDTO(
            @JsonProperty("name") String name,
            @JsonProperty("street") String street,
            @JsonProperty("houseNumber") int houseNumber,
            @JsonProperty("postalCode") int postalCode,
            @JsonProperty("approved") boolean approved,
            @JsonProperty("customerID") String customerID,
            @JsonProperty("iban") String IBAN,
            @JsonProperty("billingCycle") String billingCycle,
            @JsonProperty("outstandingAmount") Double outstandingAmount,
            @JsonProperty("email") String email
            ) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.approved = approved;

        this.customerID = customerID;
        this.IBAN = IBAN;
        this.billingCycle = billingCycle;
        this.outstandingAmount = outstandingAmount;
        this.email = email;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getEmail() {
        return email;
    }
}
