package ch.unisg.edpo.eau.customerApproval.application.domain;

public class Customer {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;

    public Customer(String name, String street, int houseNumber, int postalCode) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
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
}
