package dev.kaldiroglu.hexagonal.ayvalikbank.domain.service.account;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.account.Currency;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.account.Money;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.customer.CustomerTier;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class TransferDomainServiceTest {

    private final TransferDomainService service = new TransferDomainService();

    @Test
    void sameCustomerTransferIsFreeRegardlessOfTier() {
        Money fee = service.calculateFee(Money.of(1000.0, Currency.USD), true,
                new BigDecimal("1.0"), CustomerTier.STANDARD);
        assertThat(fee.amount()).isEqualByComparingTo("0.00");
    }

    @Test
    void standardTierPaysFullFee() {
        Money fee = service.calculateFee(Money.of(1000.0, Currency.USD), false,
                new BigDecimal("1.0"), CustomerTier.STANDARD);
        assertThat(fee.amount()).isEqualByComparingTo("10.00");
    }

    @Test
    void premiumTierPaysHalfFee() {
        Money fee = service.calculateFee(Money.of(1000.0, Currency.USD), false,
                new BigDecimal("1.0"), CustomerTier.PREMIUM);
        assertThat(fee.amount()).isEqualByComparingTo("5.00");
    }

    @Test
    void privateTierPaysNoFee() {
        Money fee = service.calculateFee(Money.of(1000.0, Currency.USD), false,
                new BigDecimal("1.0"), CustomerTier.PRIVATE);
        assertThat(fee.amount()).isEqualByComparingTo("0.00");
    }

    @Test
    void rejectsTransferAboveStandardCap() {
        assertThatThrownBy(() -> service.requireTransferWithinLimit(
                Money.of(5001.0, Currency.USD), CustomerTier.STANDARD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("STANDARD")
                .hasMessageContaining("5000");
    }

    @Test
    void allowsTransferAtExactlyTheCap() {
        assertThatCode(() -> service.requireTransferWithinLimit(
                Money.of(5000.0, Currency.USD), CustomerTier.STANDARD))
                .doesNotThrowAnyException();
    }

    @Test
    void privateTierTransferIsUnlimited() {
        assertThatCode(() -> service.requireTransferWithinLimit(
                Money.of(1_000_000.0, Currency.USD), CustomerTier.PRIVATE))
                .doesNotThrowAnyException();
    }

    @Test
    void rejectsWithdrawalAbovePremiumCap() {
        assertThatThrownBy(() -> service.requireWithdrawalWithinLimit(
                Money.of(25001.0, Currency.USD), CustomerTier.PREMIUM))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("PREMIUM");
    }

    @Test
    void privateTierWithdrawalIsUnlimited() {
        assertThatCode(() -> service.requireWithdrawalWithinLimit(
                Money.of(1_000_000.0, Currency.USD), CustomerTier.PRIVATE))
                .doesNotThrowAnyException();
    }
}
