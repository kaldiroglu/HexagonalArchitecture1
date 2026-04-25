package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.request;

import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.customer.CustomerTier;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerTierRequest(@NotNull CustomerTier tier) {}
