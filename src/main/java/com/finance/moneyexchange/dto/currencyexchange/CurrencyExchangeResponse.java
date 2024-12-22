package com.finance.moneyexchange.dto.currencyexchange;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record CurrencyExchangeResponse(String table, String currency, String code, List<Rate> rates) {
    @Builder
    public record Rate(String no, String effectiveDate, double bid, double ask) {}
}