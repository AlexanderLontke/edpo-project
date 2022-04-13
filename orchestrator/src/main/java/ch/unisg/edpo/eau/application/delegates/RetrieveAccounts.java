package ch.unisg.edpo.eau.application.delegates;

import ch.unisg.edpo.eau.application.port.out.QueryAccruedAccountsPort;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.*;

@Named
public class RetrieveAccounts implements JavaDelegate {
    @Autowired
    QueryAccruedAccountsPort queryAccruedAccountsPort;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Optional<String> listStringOptional = queryAccruedAccountsPort.retrieveAccountsDueToday();

        if (listStringOptional.isEmpty()) return;

        //TODO handling of response should be improved
        String listString = listStringOptional.get().replaceAll("\\s+","");
        String[] accountNumbers = listString.substring(1, listString.length() - 1).split(",");
        delegateExecution.setVariable("accountNumberList", Arrays.asList(accountNumbers));
    }
}