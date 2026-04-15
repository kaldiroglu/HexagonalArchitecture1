package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Account;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.CustomerId;

import java.util.List;

public interface ListAccountsUseCase {
    List<Account> listAccounts(CustomerId ownerId);
}
