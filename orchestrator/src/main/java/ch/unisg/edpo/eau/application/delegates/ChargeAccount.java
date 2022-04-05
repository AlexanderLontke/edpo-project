package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.application.port.out.AccountChargePort;
import ch.unisg.edpo.eau.domain.Account;
import ch.unisg.edpo.eau.domain.BookingTransaction;
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
        Account account = Account.deserialize(accountString);

        BookingTransaction bookingTransaction = new BookingTransaction(account.getAccountId(), account.getIBAN(), account.getAmount());
        accountChargePort.processTransaction(bookingTransaction);
    }
}