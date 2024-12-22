package com.finance.moneyexchange.domain.currencyexchange.service;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.service.GetCustomerService;
import com.finance.moneyexchange.domain.customer.service.UpdateCustomerService;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExchangePlnToUsdServiceTest {
    @Mock
    private GetCurrencyExchangeRateService getCurrencyExchangeRateService;
    @Mock
    private GetCustomerService getCustomerService;
    @Mock
    private UpdateCustomerService updateCustomerService;
    @InjectMocks
    private ExchangePlnToUsdService exchangePlnToUsdService;

    @Test
    void shouldExchangeCurrencySuccessfully() {
        UUID uuid = UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea");

        Mockito.when(getCustomerService.getCustomer(uuid)).thenReturn(CUSTOMER_1);
        Mockito.when(getCurrencyExchangeRateService.getTodayUsdCurrencyExchange()).thenReturn(CURRENCY_EXCHANGE_RESPONSE_1);
        Mockito.when(updateCustomerService.updateCustomer(Mockito.any())).thenReturn(CUSTOMER_UPDATED_1);

        Customer customer = exchangePlnToUsdService.exchangePlnToUsd(uuid);

        assertEquals(customer.uuid(), uuid);
        assertEquals(customer.firstName(), CUSTOMER_UPDATED_1.firstName());
        assertEquals(customer.lastName(), CUSTOMER_UPDATED_1.lastName());
        assertEquals(customer.balancePln(), CUSTOMER_UPDATED_1.balancePln());
        assertEquals(customer.balanceUsd(), CUSTOMER_UPDATED_1.balanceUsd());
    }

    @Test
    void shouldThrowExceptionWhenBalancePlnIs0() {
        UUID uuid = UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea");
        Mockito.when(getCustomerService.getCustomer(uuid)).thenReturn(CUSTOMER_1.toBuilder()
                .balancePln(BigDecimal.ZERO)
                .build());
        Mockito.when(getCurrencyExchangeRateService.getTodayUsdCurrencyExchange()).thenReturn(CURRENCY_EXCHANGE_RESPONSE_1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exchangePlnToUsdService.exchangePlnToUsd(uuid)
        );

        assertEquals("Balance in PLN must be greater than zero.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRatesAreNotAvailable() {
        UUID uuid = UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea");
        Mockito.when(getCustomerService.getCustomer(uuid)).thenReturn(CUSTOMER_1);
        Mockito.when(getCurrencyExchangeRateService.getTodayUsdCurrencyExchange()).thenReturn(CURRENCY_EXCHANGE_RESPONSE_1.toBuilder()
                        .rates(List.of())
                .build());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exchangePlnToUsdService.exchangePlnToUsd(uuid)
        );

        assertEquals("Rates are not available in NBP feed.", exception.getMessage());
    }


    private static final Customer CUSTOMER_1 = Customer.builder()
            .uuid(UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea"))
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("100"))
            .balanceUsd(BigDecimal.ZERO)
            .build();

    private static final Customer CUSTOMER_UPDATED_1 = Customer.builder()
            .uuid(UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea"))
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(BigDecimal.ZERO)
            .balanceUsd(new BigDecimal("26.54"))
            .build();

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
