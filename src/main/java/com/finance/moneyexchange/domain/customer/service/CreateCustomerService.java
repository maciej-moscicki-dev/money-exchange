package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.CreateCustomerPort;
import com.finance.moneyexchange.dto.customer.NewCustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateCustomerService {
    private final CreateCustomerPort createCustomerPort;

    public Customer createCustomer(NewCustomerDto newCustomerDto) {
        validateNewCustomerInput(newCustomerDto);

        Customer customer = CustomerMapper.mapCustomerEntityToCustomer(createCustomerPort.createCustomer(Customer.builder()
                .firstName(newCustomerDto.firstName().toUpperCase())
                .lastName(newCustomerDto.lastName().toUpperCase())
                .balancePln(newCustomerDto.balancePln())
                .build()));
        log.info("Created customer: {}", customer );
        return customer;
    }


    private void validateNewCustomerInput(NewCustomerDto customerDto) {
        if(customerDto.firstName() == null) {
            throw new IllegalArgumentException("Field firstName is required");
        }

        if(customerDto.lastName() == null ) {
            throw new IllegalArgumentException("Field lastName is required");
        }

        if(customerDto.balancePln() == null) {
            throw new IllegalArgumentException("Field balancePln is required");
        }

        if(customerDto.balancePln().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Field balancePln must be greater than zero");
        }
    }
}
