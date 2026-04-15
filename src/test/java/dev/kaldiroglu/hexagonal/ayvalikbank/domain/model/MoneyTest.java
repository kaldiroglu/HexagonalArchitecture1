package dev.kaldiroglu.hexagonal.ayvalikbank.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithValidAmountAndCurrency() {
        Money money = Money.of(100.0, Currency.USD);
        assertThat(money.amount()).isEqualByComparingTo("100.00");
        assertThat(money.currency()).isEqualTo(Currency.USD);
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThatThrownBy(() -> Money.of(-1.0, Currency.EUR))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("negative");
    }

    @Test
    void shouldAddMoneyOfSameCurrency() {
        Money a = Money.of(100.0, Currency.USD);
        Money b = Money.of(50.0, Currency.USD);
        assertThat(a.add(b).amount()).isEqualByComparingTo("150.00");
    }

    @Test
    void shouldRejectAddingDifferentCurrencies() {
        Money usd = Money.of(100.0, Currency.USD);
        Money eur = Money.of(50.0, Currency.EUR);
        assertThatThrownBy(() -> usd.add(eur))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency mismatch");
    }

    @Test
    void shouldSubtractMoneyOfSameCurrency() {
        Money a = Money.of(100.0, Currency.TL);
        Money b = Money.of(30.0, Currency.TL);
        assertThat(a.subtract(b).amount()).isEqualByComparingTo("70.00");
    }

    @Test
    void shouldRejectSubtractingMoreThanAvailable() {
        Money a = Money.of(50.0, Currency.USD);
        Money b = Money.of(100.0, Currency.USD);
        assertThatThrownBy(() -> a.subtract(b))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient");
    }

    @Test
    void shouldReturnZeroMoneyForCurrency() {
        Money zero = Money.zero(Currency.EUR);
        assertThat(zero.amount()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(zero.currency()).isEqualTo(Currency.EUR);
    }
}
