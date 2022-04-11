package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.application.port.out.SendEmailPort;
import ch.unisg.edpo.eau.domain.Account;
import ch.unisg.edpo.eau.domain.Email;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named
public class SendConfirmationEmail implements JavaDelegate {
    @Autowired
    SendEmailPort sendEmailPort;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accountString = (String) delegateExecution.getVariable("account");
        Account account = Account.deserialize(accountString);

        //TODO should be email not customerID
        Email email = new Email(account.getAccountId(), "Your Bill was cleared", "We were successful in booking your last invoice of " + account.getAmount() + " off the bank on file for your Account.");

        sendEmailPort.sendEmail(email);
    }
}