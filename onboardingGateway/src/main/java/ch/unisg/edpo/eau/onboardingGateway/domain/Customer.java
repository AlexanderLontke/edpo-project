package ch.unisg.edpo.eau.onboardingGateway.domain;

public class Customer {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private final boolean approved;

    public Customer(String name, String street, int houseNumber, int postalCode) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.approved = false;
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
