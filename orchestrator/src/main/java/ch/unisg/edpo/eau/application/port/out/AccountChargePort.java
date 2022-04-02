package ch.unisg.edpo.eau.application.port.out;

import ch.unisg.edpo.eau.domain.BookingTransaction;

public interface AccountChargePort {
    void processTransaction(BookingTransaction bookingTransaction);
}
