package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Customer;

import java.util.List;

public interface ListCustomersUseCase {
    List<Customer> listCustomers();
}
