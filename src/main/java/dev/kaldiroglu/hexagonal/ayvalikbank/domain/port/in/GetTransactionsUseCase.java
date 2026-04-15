package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.AccountId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Transaction;

import java.util.List;

public interface GetTransactionsUseCase {
    List<Transaction> getTransactions(AccountId accountId);
}
