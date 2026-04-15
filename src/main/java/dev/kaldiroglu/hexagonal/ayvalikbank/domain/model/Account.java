package dev.kaldiroglu.hexagonal.ayvalikbank.domain.model;

import java.math.BigDecimal;

public class Account {
    private final AccountId id;
    private final CustomerId ownerId;
    private final Currency currency;
    private Money balance;

    public Account(AccountId id, CustomerId ownerId, Currency currency, Money balance) {
        if (!balance.currency().equals(currency))
            throw new IllegalArgumentException("Balance currency must match account currency");
        this.id = id;
        this.ownerId = ownerId;
        this.currency = currency;
        this.balance = balance;
    }

    public static Account open(CustomerId ownerId, Currency currency) {
        return new Account(AccountId.generate(), ownerId, currency, Money.zero(currency));
    }

    public Transaction deposit(Money amount) {
        if (!amount.currency().equals(this.currency))
            throw new IllegalArgumentException("Deposit currency " + amount.currency() + " does not match account currency " + this.currency);
        this.balance = this.balance.add(amount);
        return Transaction.create(this.id, TransactionType.DEPOSIT, amount, "Deposit");
    }

    public Transaction withdraw(Money amount) {
        if (!amount.currency().equals(this.currency))
            throw new IllegalArgumentException("Withdrawal currency " + amount.currency() + " does not match account currency " + this.currency);
        if (!this.balance.isGreaterThanOrEqualTo(amount))
            throw new IllegalArgumentException("Insufficient funds");
        this.balance = this.balance.subtract(amount);
        return Transaction.create(this.id, TransactionType.WITHDRAWAL, amount, "Withdrawal");
    }

    /**
     * Debits the account for an outbound transfer (fee already included in totalDebit).
     */
    public Transaction transferOut(Money amount, Money fee, String targetAccountId) {
        if (!amount.currency().equals(this.currency))
            throw new IllegalArgumentException("Transfer currency does not match account currency");
        Money totalDebit = fee.amount().compareTo(BigDecimal.ZERO) > 0 ? amount.add(fee) : amount;
        if (!this.balance.isGreaterThanOrEqualTo(totalDebit))
            throw new IllegalArgumentException("Insufficient funds for transfer including fee");
        this.balance = this.balance.subtract(totalDebit);
        String desc = "Transfer out to account " + targetAccountId +
                (fee.amount().compareTo(BigDecimal.ZERO) > 0 ? " (fee: " + fee + ")" : "");
        return Transaction.create(this.id, TransactionType.TRANSFER_OUT, amount, desc);
    }

    /**
     * Credits the account for an inbound transfer.
     */
    public Transaction transferIn(Money amount, String sourceAccountId) {
        if (!amount.currency().equals(this.currency))
            throw new IllegalArgumentException("Transfer currency does not match account currency");
        this.balance = this.balance.add(amount);
        return Transaction.create(this.id, TransactionType.TRANSFER_IN, amount, "Transfer in from account " + sourceAccountId);
    }

    public AccountId getId() { return id; }
    public CustomerId getOwnerId() { return ownerId; }
    public Currency getCurrency() { return currency; }
    public Money getBalance() { return balance; }
}
