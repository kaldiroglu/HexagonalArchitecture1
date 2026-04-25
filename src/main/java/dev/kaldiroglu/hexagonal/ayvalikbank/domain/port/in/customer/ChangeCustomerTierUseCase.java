package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in.customer;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.customer.CustomerId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.customer.CustomerTier;

public interface ChangeCustomerTierUseCase {
    record Command(CustomerId customerId, CustomerTier tier) {}
    void changeCustomerTier(Command command);
}
