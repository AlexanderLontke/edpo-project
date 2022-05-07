package ch.unisg.edpo.eau.eventstrom.model.join;


import ch.unisg.edpo.eau.eventstrom.model.Customer;
import ch.unisg.edpo.eau.eventstrom.model.EnergyMeter;

public class CustomerAndEnergy {
    public java.lang.CharSequence message_id;
    public java.lang.CharSequence message_type;
    public long time_start;
    public java.lang.CharSequence time_end;
    public double deltaE;
    public java.lang.CharSequence deviceId;
    public java.lang.CharSequence customer_id;
    public java.lang.CharSequence customerName;
    public int customerPostalCode;

    public CustomerAndEnergy(EnergyMeter energyMeter, Customer customer) {
        // Energy meter data
        this.message_id = energyMeter.getMessageId();
        this.message_type = energyMeter.getMessageType();
        this.time_start = energyMeter.getTimeStart();
        this.time_end = energyMeter.getTimeEnd();
        this.deltaE = energyMeter.getDeltaE();
        this.deviceId = energyMeter.getDeviceId();

        // Customer data
        this.customer_id = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.customerPostalCode = customer.getCustomerPostalCode();
    }

    public CharSequence getCustomer_id() {
        return customer_id;
    }
}
