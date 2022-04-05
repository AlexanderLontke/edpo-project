package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class MongoCustomerDocument {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private final boolean approved;

    public MongoCustomerDocument(String name, String street, int houseNumber, int postalCode, boolean approved) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.approved = approved;
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

    public int getPostalCode() {
        return postalCode;
    }

    public boolean isApproved() {
        return approved;
    }
}
