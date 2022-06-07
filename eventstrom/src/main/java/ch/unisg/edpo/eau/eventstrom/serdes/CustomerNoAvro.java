package ch.unisg.edpo.eau.eventstrom.serdes;

public class CustomerNoAvro {
    private String customer_id;
    private String customer_name;
    private int customer_postal_code;

    public CustomerNoAvro(String customer_id, String customer_name, int customer_postal_code) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_postal_code = customer_postal_code;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getCustomer_postal_code() {
        return customer_postal_code;
    }

    public void setCustomer_postal_code(int customer_postal_code) {
        this.customer_postal_code = customer_postal_code;
    }

    @Override
    public String toString() {
        return "CustomerNoAvro{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_postal_code=" + customer_postal_code +
                '}';
    }
}
