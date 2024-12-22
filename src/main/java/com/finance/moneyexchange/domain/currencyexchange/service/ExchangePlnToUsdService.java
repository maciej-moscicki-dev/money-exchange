package com.finance.moneyexchange.domain.currencyexchange.service;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.service.GetCustomerService;
import com.finance.moneyexchange.domain.customer.service.UpdateCustomerService;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangePlnToUsdService {
    private final GetCurrencyExchangeRateService getCurrencyExchangeRateService;
    private final GetCustomerService getCustomerService;
    private final UpdateCustomerService updateCustomerService;

    public Customer exchangePlnToUsd(UUID uuid) {
        Customer customer = getCustomerService.getCustomer(uuid);
        CurrencyExchangeResponse todayUsdExchangeRate = getCurrencyExchangeRateService.getTodayUsdCurrencyExchange();

        BigDecimal askRate = BigDecimal.valueOf(todayUsdExchangeRate.rates().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rates are not available in NBP feed."))
                .ask());

        if(customer.balancePln().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Balance in PLN must be greater than zero.");
        }

        BigDecimal convertedBalanceUsd = customer.balancePln().divide(askRate, 2, RoundingMode.HALF_UP);

        customer = customer.toBuilder()
                .balancePln(new BigDecimal("0"))
                .balanceUsd(convertedBalanceUsd)
                .build();

        updateCustomerService.updateCustomer(customer);

        return customer;
    }
}
