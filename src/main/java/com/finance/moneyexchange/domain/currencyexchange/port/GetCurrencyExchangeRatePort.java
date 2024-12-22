package com.finance.moneyexchange.domain.currencyexchange.port;

import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;

public interface GetCurrencyExchangeRatePort {
    CurrencyExchangeResponse getTodayUsdExchangeRate();
}
