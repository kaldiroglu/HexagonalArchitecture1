package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.AccountId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Money;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Transaction;

public interface WithdrawMoneyUseCase {
    record Command(AccountId accountId, Money amount) {}
    Transaction withdraw(Command command);
}
