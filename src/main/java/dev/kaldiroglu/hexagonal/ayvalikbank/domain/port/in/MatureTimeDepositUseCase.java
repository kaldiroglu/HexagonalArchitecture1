package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.AccountId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Transaction;

public interface MatureTimeDepositUseCase {
    Transaction mature(AccountId accountId);
}
