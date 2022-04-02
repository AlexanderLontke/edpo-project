package ch.unisg.edpo.eau.onboardingGateway.application.adapter.http.dto;

import ch.unisg.edpo.eau.onboardingGateway.application.domain.Customer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerDTO {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;

    public Customer toEntity(){
        return new Customer(
                this.name,
                this.street,
                this.houseNumber,
                this.postalCode
        );
    }
}
