package ch.unisg.edpo.eau.application.port.out;

import java.util.Optional;

public interface QueryAccountInformationPort {
    Optional<String> retrieveAccountInformation(String accountId);
}
