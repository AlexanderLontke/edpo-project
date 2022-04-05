package ch.unisg.edpo.eau.application.port.out;

import java.util.Optional;

public interface QueryAccruedAccountsPort {
    Optional<String> retrieveAccountsDueToday();
}
