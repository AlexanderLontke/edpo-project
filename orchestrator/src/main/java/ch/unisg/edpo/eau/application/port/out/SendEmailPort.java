package ch.unisg.edpo.eau.application.port.out;

import ch.unisg.edpo.eau.domain.Email;

public interface SendEmailPort {
    void sendEmail(Email email);
}
