package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.adapter.out.web.dto.CustomerDTO;
import ch.unisg.edpo.eau.adapter.out.web.dto.EmailDTO;
import ch.unisg.edpo.eau.application.port.out.SendEmailPort;
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
        CustomerDTO customer = CustomerDTO.deserialize(accountString);

        EmailDTO emailDTO = new EmailDTO(customer.getEmail(), "Your Bill was cleared", "We were successful in booking your last invoice of " + customer.getOutstandingAmount().toString() + " off the bank on file for your Account.");

        sendEmailPort.sendEmail(emailDTO);
    }
}