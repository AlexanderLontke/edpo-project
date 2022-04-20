package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.adapter.out.web.dto.CustomerDTO;
import ch.unisg.edpo.eau.application.port.out.AccountChargePort;
import ch.unisg.edpo.eau.adapter.out.web.dto.BookingTransactionDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import javax.inject.Named;

@Named
public class ChargeAccount implements JavaDelegate {
    @Autowired
    AccountChargePort accountChargePort;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accountString = (String) delegateExecution.getVariable("account");
        CustomerDTO customer = CustomerDTO.deserialize(accountString);

        BookingTransactionDTO bookingTransaction = new BookingTransactionDTO(customer.getCustomerID(), customer.getIBAN(), customer.getOutstandingAmount());
        accountChargePort.processTransaction(bookingTransaction);
    }
}