package ch.unisg.edpo.eau.application.port.out;

import ch.unisg.edpo.eau.adapter.out.web.dto.EmailDTO;

public interface SendEmailPort {
    void sendEmail(EmailDTO emailDTO);
}
