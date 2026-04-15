package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Customer;

public interface CreateCustomerUseCase {
    record Command(String name, String email, String rawPassword) {}
    Customer createCustomer(Command command);
}
