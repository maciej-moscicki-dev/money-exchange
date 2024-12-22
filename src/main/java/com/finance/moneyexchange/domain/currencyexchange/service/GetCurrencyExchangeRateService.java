package com.finance.moneyexchange.domain.currencyexchange.service;

import com.finance.moneyexchange.domain.currencyexchange.port.GetCurrencyExchangeRatePort;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCurrencyExchangeRateService {
    private final GetCurrencyExchangeRatePort getCurrencyExchangeRatePort;

    public CurrencyExchangeResponse getTodayUsdCurrencyExchange() {
        return getCurrencyExchangeRatePort.getTodayUsdExchangeRate();
    }
}
