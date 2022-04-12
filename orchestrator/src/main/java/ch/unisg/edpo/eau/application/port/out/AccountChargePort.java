package ch.unisg.edpo.eau.application.port.out;

import ch.unisg.edpo.eau.adapter.out.web.dto.BookingTransactionDTO;

public interface AccountChargePort {
    void processTransaction(BookingTransactionDTO bookingTransaction);
}
