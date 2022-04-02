package ch.unisg.edpo.eau.customerApproval.application.adapter.messaging.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private boolean approved;

    @JsonCreator
    public CustomerDTO(
            @JsonProperty("name") String name,
            @JsonProperty("street") String street,
            @JsonProperty("houseNumber") int houseNumber,
            @JsonProperty("postalCode") int postalCode,
            @JsonProperty("approved") boolean approved) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.approved = approved;
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

    public void setApproved(boolean approved) {
        this.approved = approved;
    }


}
