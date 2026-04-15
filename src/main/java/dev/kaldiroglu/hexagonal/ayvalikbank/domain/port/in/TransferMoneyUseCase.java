package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.AccountId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Money;

public interface TransferMoneyUseCase {
    record Command(AccountId sourceAccountId, AccountId targetAccountId, Money amount) {}
    void transfer(Command command);
}
