package dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.customer;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class CustomerTierTest {

    @Test
    void standardHasFullFeeAndModestCaps() {
        assertThat(CustomerTier.STANDARD.feeMultiplier()).isEqualByComparingTo("1.00");
        assertThat(CustomerTier.STANDARD.maxPerTransfer()).contains(new BigDecimal("5000"));
        assertThat(CustomerTier.STANDARD.maxPerWithdrawal()).contains(new BigDecimal("5000"));
    }

    @Test
    void premiumHalvesFeeAndRaisesCaps() {
        assertThat(CustomerTier.PREMIUM.feeMultiplier()).isEqualByComparingTo("0.50");
        assertThat(CustomerTier.PREMIUM.maxPerTransfer()).contains(new BigDecimal("50000"));
        assertThat(CustomerTier.PREMIUM.maxPerWithdrawal()).contains(new BigDecimal("25000"));
    }

    @Test
    void privateIsFreeAndUnlimited() {
        assertThat(CustomerTier.PRIVATE.feeMultiplier()).isEqualByComparingTo("0.00");
        assertThat(CustomerTier.PRIVATE.maxPerTransfer()).isEmpty();
        assertThat(CustomerTier.PRIVATE.maxPerWithdrawal()).isEmpty();
    }
}
