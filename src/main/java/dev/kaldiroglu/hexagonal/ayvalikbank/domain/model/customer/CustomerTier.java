package dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.customer;

import java.math.BigDecimal;
import java.util.Optional;

public enum CustomerTier {

    STANDARD(new BigDecimal("1.00"), new BigDecimal("5000"),  new BigDecimal("5000")),
    PREMIUM (new BigDecimal("0.50"), new BigDecimal("50000"), new BigDecimal("25000")),
    PRIVATE (new BigDecimal("0.00"), null,                    null);

    private final BigDecimal feeMultiplier;
    private final BigDecimal maxPerTransfer;
    private final BigDecimal maxPerWithdrawal;

    CustomerTier(BigDecimal feeMultiplier, BigDecimal maxPerTransfer, BigDecimal maxPerWithdrawal) {
        this.feeMultiplier = feeMultiplier;
        this.maxPerTransfer = maxPerTransfer;
        this.maxPerWithdrawal = maxPerWithdrawal;
    }

    public BigDecimal feeMultiplier() {
        return feeMultiplier;
    }

    public Optional<BigDecimal> maxPerTransfer() {
        return Optional.ofNullable(maxPerTransfer);
    }

    public Optional<BigDecimal> maxPerWithdrawal() {
        return Optional.ofNullable(maxPerWithdrawal);
    }
}
