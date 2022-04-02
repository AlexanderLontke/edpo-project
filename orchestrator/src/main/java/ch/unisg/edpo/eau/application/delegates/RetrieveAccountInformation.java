package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.application.port.out.QueryAccountInformationPort;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Named
public class RetrieveAccountInformation implements JavaDelegate {
    @Autowired
    QueryAccountInformationPort queryAccountInformationPort;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> accountNumbers = (List) delegateExecution.getVariable("accountNumberList");
        List<String> serializedAccounts = new ArrayList<>();

        for (String accountNumber : accountNumbers) {
            Optional<String> serializedAccountOptional = queryAccountInformationPort.retrieveAccountInformation(accountNumber);
            serializedAccountOptional.ifPresent(serializedAccounts::add);
        }

        delegateExecution.setVariable("accountList", serializedAccounts);
    }
}