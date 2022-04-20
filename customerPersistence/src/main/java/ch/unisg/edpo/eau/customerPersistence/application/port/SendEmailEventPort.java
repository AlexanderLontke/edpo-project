package ch.unisg.edpo.eau.customerPersistence.application.port;

import ch.unisg.edpo.eau.customerPersistence.domain.Customer;

public interface SendEmailEventPort {
    public void sendEmail(Customer customer);
}
