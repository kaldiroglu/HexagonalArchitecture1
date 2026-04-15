package dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.out;

import java.math.BigDecimal;

public interface SettingsRepositoryPort {
    BigDecimal getTransferFeePercent();
    void setTransferFeePercent(BigDecimal percent);
}
