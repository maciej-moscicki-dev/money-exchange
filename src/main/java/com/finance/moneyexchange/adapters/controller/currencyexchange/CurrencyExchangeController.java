package com.finance.moneyexchange.adapters.controller.currencyexchange;

import com.finance.moneyexchange.domain.currencyexchange.service.ExchangePlnToUsdService;
import com.finance.moneyexchange.domain.currencyexchange.service.ExchangeUsdToPlnService;
import com.finance.moneyexchange.domain.currencyexchange.service.GetCurrencyExchangeRateService;
import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.dto.currencyexchange.CurrencyExchangeResponse;
import com.finance.moneyexchange.dto.customer.CustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency-exchange")
public class CurrencyExchangeController {
   private final GetCurrencyExchangeRateService getCurrencyExchangeRateService;
   private final ExchangePlnToUsdService exchangePlnToUsdService;
   private final ExchangeUsdToPlnService exchangeUsdToPlnService;

    @RequestMapping(value = "/today-usd-exchange-rate", method = RequestMethod.GET)
    @Operation(
            summary = "Get today's USD exchange rate",
            description = "This endpoint retrieves the current exchange rate of USD for today."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Today USD exchange rate was not found. Please verify if it was already published.",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<CurrencyExchangeResponse> getTodayUsdExchangeRate() {
        return new ResponseEntity<>(
                getCurrencyExchangeRateService.getTodayUsdCurrencyExchange(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/pln-to-usd", method = RequestMethod.POST)
    @Operation(
            summary = "Convert PLN to USD",
            description = "This endpoint converts a customer's PLN amount to USD using the provided UUID."
    )
    public ResponseEntity<CustomerDto> convertPlnToUsd(@RequestParam UUID uuid) {
        return new ResponseEntity<>(
                mapCustomerToCustomerDto(exchangePlnToUsdService.exchangePlnToUsd(uuid)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/usd-to-pln", method = RequestMethod.POST)
    @Operation(
            summary = "Convert USD to PLN",
            description = "This endpoint converts a customer's USD amount to PLN using the provided UUID."
    )
    public ResponseEntity<CustomerDto> convertUsdToPln(@RequestParam UUID uuid) {
        return new ResponseEntity<>(
                mapCustomerToCustomerDto(exchangeUsdToPlnService.exchangeUsdToPln(uuid)),
                HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private CustomerDto mapCustomerToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .uuid(customer.uuid())
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .balancePln(customer.balancePln())
                .balanceUsd(customer.balanceUsd())
                .build();
    }
}
