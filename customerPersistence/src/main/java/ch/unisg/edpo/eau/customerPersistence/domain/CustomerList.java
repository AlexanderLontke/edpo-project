package ch.unisg.edpo.eau.customerPersistence.domain;

import java.util.List;

public class CustomerList {
    private String Id;
    private List<Customer> customerList;

    public CustomerList(String id, List<Customer> customerList) {
        Id = id;
        this.customerList = customerList;
    }

    public String getId() {
        return Id;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }
}
