package dev.kaldiroglu.hexagonal.ayvalikbank.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountTest {

    private Account openUsdAccount() {
        return Account.open(CustomerId.generate(), Currency.USD);
    }

    @Test
    void shouldOpenAccountWithZeroBalance() {
        Account account = openUsdAccount();
        assertThat(account.getBalance().amount()).isEqualByComparingTo("0.00");
        assertThat(account.getBalance().currency()).isEqualTo(Currency.USD);
    }

    @Test
    void shouldDepositAndIncreaseBalance() {
        Account account = openUsdAccount();
        Transaction tx = account.deposit(Money.of(500.0, Currency.USD));
        assertThat(account.getBalance().amount()).isEqualByComparingTo("500.00");
        assertThat(tx.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(tx.getAmount().amount()).isEqualByComparingTo("500.00");
    }

    @Test
    void shouldWithdrawAndDecreaseBalance() {
        Account account = openUsdAccount();
        account.deposit(Money.of(500.0, Currency.USD));
        Transaction tx = account.withdraw(Money.of(200.0, Currency.USD));
        assertThat(account.getBalance().amount()).isEqualByComparingTo("300.00");
        assertThat(tx.getType()).isEqualTo(TransactionType.WITHDRAWAL);
    }

    @Test
    void shouldRejectWithdrawalExceedingBalance() {
        Account account = openUsdAccount();
        account.deposit(Money.of(100.0, Currency.USD));
        assertThatThrownBy(() -> account.withdraw(Money.of(200.0, Currency.USD)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient");
    }

    @Test
    void shouldRejectDepositWithWrongCurrency() {
        Account account = openUsdAccount();
        assertThatThrownBy(() -> account.deposit(Money.of(100.0, Currency.EUR)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("currency");
    }

    @Test
    void shouldTransferOutWithFeeDeducted() {
        Account account = openUsdAccount();
        account.deposit(Money.of(1000.0, Currency.USD));
        account.transferOut(Money.of(200.0, Currency.USD), Money.of(2.0, Currency.USD), "target-id");
        assertThat(account.getBalance().amount()).isEqualByComparingTo("798.00");
    }

    @Test
    void shouldTransferInAndIncreaseBalance() {
        Account account = openUsdAccount();
        account.transferIn(Money.of(300.0, Currency.USD), "source-id");
        assertThat(account.getBalance().amount()).isEqualByComparingTo("300.00");
    }
}
