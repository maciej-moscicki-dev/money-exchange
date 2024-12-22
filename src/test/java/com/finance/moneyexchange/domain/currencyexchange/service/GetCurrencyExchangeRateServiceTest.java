package com.finance.moneyexchange.domain.currencyexchange.service;

import com.finance.moneyexchange.domain.currencyexchange.port.GetCurrencyExchangeRatePort;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetCurrencyExchangeRateServiceTest {
    @Mock
    private GetCurrencyExchangeRatePort getCurrencyExchangeRatePort;
    @InjectMocks
    private GetCurrencyExchangeRateService getCurrencyExchangeRateService;

    @Test
    void shouldGetTodayUsdCurrencyExchange() {
        Mockito.when(getCurrencyExchangeRatePort.getTodayUsdExchangeRate()).thenReturn(CURRENCY_EXCHANGE_RESPONSE_1);

        CurrencyExchangeResponse todayUsdCurrencyExchange = getCurrencyExchangeRateService.getTodayUsdCurrencyExchange();

        Assertions.assertEquals(CURRENCY_EXCHANGE_RESPONSE_1, todayUsdCurrencyExchange);
    }

    private static final CurrencyExchangeResponse CURRENCY_EXCHANGE_RESPONSE_1 = CurrencyExchangeResponse.builder()
            .table("c")
            .currency("dolar amerykański")
            .code("USD")
            .rates(List.of(CurrencyExchangeResponse.Rate.builder()
                    .no("064/C/NBP/2016")
                    .effectiveDate("2016-04-04")
                    .bid(3.6929)
                    .ask(3.7675)
                    .build()))
            .build();
}
