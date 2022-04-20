package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.adapter.out.web.dto.CustomerDTO;
import ch.unisg.edpo.eau.adapter.out.web.dto.EmailDTO;
import ch.unisg.edpo.eau.application.port.out.SendEmailPort;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named
public class SendErrorEmail implements JavaDelegate {
    @Autowired
    SendEmailPort sendEmailPort;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accountString = (String) delegateExecution.getVariable("account");
        CustomerDTO customer = CustomerDTO.deserialize(accountString);

        EmailDTO emailDTO = new EmailDTO(customer.getEmail(), "There was an issue with your invoice", "Unfortunately we were not successful in booking your last invoice of " + customer.getOutstandingAmount().toString() + " off the bank on file for your Account. A support agent will have a look at it and might get back to you with further requests.");

        sendEmailPort.sendEmail(emailDTO);
    }
}