package com.finance.moneyexchange.adapters.repository.customer;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.UpdateCustomerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UpdateCustomerRepository implements UpdateCustomerPort {
    private final CustomerAdapter customerAdapter;

    public CustomerEntity updateCustomer(Customer customer) {
        CustomerEntity customerEntity = customerAdapter.findById(customer.uuid().toString())
                .orElseThrow(() -> new NoSuchElementException(String.format("Customer with UUID: %s not found.", customer.uuid())));

        return customerAdapter.save(customerEntity.toBuilder()
                .balancePln(customer.balancePln())
                .balanceUsd(customer.balanceUsd())
                .build());
    }
}
