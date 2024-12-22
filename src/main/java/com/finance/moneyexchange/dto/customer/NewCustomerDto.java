package com.finance.moneyexchange.dto.customer;

import lombok.Builder;
import java.math.BigDecimal;

@Builder(toBuilder = true)
public record NewCustomerDto(
        String firstName,
        String lastName,
        BigDecimal balancePln
) {}
