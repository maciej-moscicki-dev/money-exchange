package com.finance.moneyexchange.adapters.rest;

import com.finance.moneyexchange.domain.currencyexchange.port.GetCurrencyExchangeRatePort;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class GetCurrencyExchangeRestAdapter implements GetCurrencyExchangeRatePort {
    private final String nbpApiUrlProperty;
    private final RestTemplate restTemplate;

    public GetCurrencyExchangeRestAdapter(@Value("${nbp.api.url}") String nbpApiUrlProperty, RestTemplate restTemplate) {
        this.nbpApiUrlProperty = nbpApiUrlProperty;
        this.restTemplate = restTemplate;
    }

    public CurrencyExchangeResponse getTodayUsdExchangeRate() {
        try {
            return restTemplate.getForObject(nbpApiUrlProperty, CurrencyExchangeResponse.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}
