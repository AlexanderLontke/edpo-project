package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class MongoCustomerDocument {
    String name;
    String street;
    int houseNumber;
    int postalCode;
    boolean approved;

    String customerID;
    String IBAN;
    String billingCycle;
    Double outstandingAmount;
    String email;

    public MongoCustomerDocument(String name, String street, int houseNumber, int postalCode, boolean approved, String customerID, String IBAN, String billingCycle, Double outstandingAmount, String email) {
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
