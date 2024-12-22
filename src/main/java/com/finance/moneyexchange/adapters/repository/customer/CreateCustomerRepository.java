package com.finance.moneyexchange.adapters.repository.customer;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.CreateCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CreateCustomerRepository implements CreateCustomerPort {
    private final CustomerAdapter customerAdapter;

    public CustomerEntity createCustomer(Customer customer) {
        return customerAdapter.save(CustomerEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .balancePln(customer.balancePln())
                .balanceUsd(BigDecimal.ZERO)
                .build());
    }
}
