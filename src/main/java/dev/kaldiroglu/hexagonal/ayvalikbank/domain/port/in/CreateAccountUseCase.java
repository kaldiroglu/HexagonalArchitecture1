package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Account;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Currency;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.CustomerId;

public interface CreateAccountUseCase {
    record Command(CustomerId ownerId, Currency currency) {}
    Account createAccount(Command command);
}
