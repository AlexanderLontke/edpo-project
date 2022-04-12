package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.adapter.out.web.dto.CustomerDTO;
import ch.unisg.edpo.eau.application.port.out.QueryAccountInformationPort;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.Optional;

@Named
public class RetrieveAccountInformation implements JavaDelegate {
    @Autowired
    QueryAccountInformationPort queryAccountInformationPort;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accountNumber = (String) delegateExecution.getVariable("accountId");

        Optional<String> serializedAccountOptional = queryAccountInformationPort.retrieveAccountInformation(accountNumber);

        //parse to CustomerDTO, remove unused fields
        if (serializedAccountOptional.isPresent()) {
            CustomerDTO customerDTO = CustomerDTO.deserialize(serializedAccountOptional.get());
            delegateExecution.setVariable("account", CustomerDTO.serialize(customerDTO));
        } else {
            throw new BpmnError("AccountInformationError", "There was an issue attempting to retrieve the Account information.");
        }
    }
}