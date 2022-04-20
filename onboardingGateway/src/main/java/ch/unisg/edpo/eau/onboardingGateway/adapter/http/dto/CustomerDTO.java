package ch.unisg.edpo.eau.onboardingGateway.adapter.http.dto;

import ch.unisg.edpo.eau.onboardingGateway.domain.Customer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerDTO {
    private final String name;
    private final String street;
    private final int houseNumber;
    private final int postalCode;
    private final String email;

    public Customer toEntity(){
        return new Customer(
                this.name,
                this.street,
                this.houseNumber,
                this.postalCode,
                this.email
        );
    }
}
