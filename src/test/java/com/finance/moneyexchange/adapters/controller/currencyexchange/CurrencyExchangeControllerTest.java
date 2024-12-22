package com.finance.moneyexchange.adapters.controller.currencyexchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.moneyexchange.domain.currencyexchange.service.ExchangePlnToUsdService;
import com.finance.moneyexchange.domain.currencyexchange.service.ExchangeUsdToPlnService;
import com.finance.moneyexchange.domain.currencyexchange.service.GetCurrencyExchangeRateService;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyExchangeController.class)
public class CurrencyExchangeControllerTest {
    @MockitoBean
    private GetCurrencyExchangeRateService getCurrencyExchangeRateService;
    @MockitoBean
    private ExchangePlnToUsdService exchangePlnToUsdService;
    @MockitoBean
    private ExchangeUsdToPlnService exchangeUsdToPlnService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetTodayUsdCurrencyExchange() throws Exception {
        Mockito.when(getCurrencyExchangeRateService.getTodayUsdCurrencyExchange()).thenReturn(CURRENCY_EXCHANGE_RESPONSE_1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/currency-exchange/today-usd-exchange-rate"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(CURRENCY_EXCHANGE_RESPONSE_1)));
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
