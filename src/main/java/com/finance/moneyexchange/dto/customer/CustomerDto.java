package com.finance.moneyexchange.dto.customer;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CustomerDto(
        UUID uuid,
        String firstName,
        String lastName,
        BigDecimal balancePln,
        BigDecimal balanceUsd
) {}
