package com.finance.moneyexchange.adapters.controller.customer;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.service.CreateCustomerService;
import com.finance.moneyexchange.domain.customer.service.GetCustomerService;
import com.finance.moneyexchange.dto.customer.CustomerDto;
import com.finance.moneyexchange.dto.customer.NewCustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    private final CreateCustomerService createCustomerService;
    private final GetCustomerService getCustomerService;


    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @Operation(
            summary = "Create a new customer",
            description = "This endpoint creates a new customer using the provided details in the `NewCustomerDto` object."
    )
    public ResponseEntity<CustomerDto> createNewCustomer(@RequestBody NewCustomerDto newCustomerDto) {
        Customer customer = createCustomerService.createCustomer(newCustomerDto);
        return new ResponseEntity<>(
                mapCustomerToCustomerDto(customer),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @Operation(
            summary = "Get customer details",
            description = "This endpoint retrieves the details of a customer by their unique ID."
    )
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") UUID id) {
        Customer customer = getCustomerService.getCustomer(id);
        return new ResponseEntity<>(
                mapCustomerToCustomerDto(customer),
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
