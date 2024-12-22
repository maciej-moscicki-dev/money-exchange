package com.finance.moneyexchange.domain.customer.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
public record Customer(
        UUID uuid,
        String firstName,
        String lastName,
        BigDecimal balancePln,
        BigDecimal balanceUsd
) {}
