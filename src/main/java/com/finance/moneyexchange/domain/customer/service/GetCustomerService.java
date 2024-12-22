package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;
import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.GetCustomerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetCustomerService {
    private final GetCustomerPort getCustomerPort;

    public Customer getCustomer(UUID id) {
        CustomerEntity customerEntity = getCustomerPort.getCustomer(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Customer with UUID: %s not found.", id)));

        return CustomerMapper.mapCustomerEntityToCustomer(customerEntity);
    }
}
