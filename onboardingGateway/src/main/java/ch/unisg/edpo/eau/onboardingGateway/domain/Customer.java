package ch.unisg.edpo.eau.onboardingGateway.domain;

import java.util.UUID;

public class Customer {
    private final String customerID;
    private final String IBAN;
    private final String billingCycle;
    private final Double outstandingAmount;

    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private final boolean approved;
    private final String email;

    public Customer(String name, String street, int houseNumber, int postalCode, String email) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.email = email;

        this.approved = false;
        this.customerID = UUID.randomUUID().toString();
        this.IBAN = "CH" + UUID.randomUUID();
        this.billingCycle = "1M";
        this.outstandingAmount = 0.0;
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

    public String getEmail() {
        return email;
    }
}
