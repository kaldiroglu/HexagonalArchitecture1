package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.CustomerId;

public interface ChangePasswordUseCase {
    record Command(CustomerId customerId, String rawNewPassword) {}
    void changePassword(Command command);
}
