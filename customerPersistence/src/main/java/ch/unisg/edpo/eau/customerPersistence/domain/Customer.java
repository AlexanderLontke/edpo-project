package ch.unisg.edpo.eau.customerPersistence.domain;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode
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

    public Customer(String customerID, String IBAN, String billingCycle, Double outstandingAmount, String name, String street, int houseNumber, int postalCode, boolean approved, String email) {
        this.customerID = customerID;
        this.IBAN = IBAN;
        this.billingCycle = billingCycle;
        this.outstandingAmount = outstandingAmount;
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.approved = approved;
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
