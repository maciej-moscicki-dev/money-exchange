package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;
import com.finance.moneyexchange.domain.customer.model.Customer;

import java.util.UUID;

public class CustomerMapper {
    protected static Customer mapCustomerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer.builder()
                .uuid(UUID.fromString(customerEntity.getUuid()))
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .balancePln(customerEntity.getBalancePln())
                .balanceUsd(customerEntity.getBalanceUsd())
                .build();
    }
}
