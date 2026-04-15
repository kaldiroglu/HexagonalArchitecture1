package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.CustomerId;

public interface DeleteCustomerUseCase {
    void deleteCustomer(CustomerId customerId);
}
