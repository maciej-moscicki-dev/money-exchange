package com.finance.moneyexchange.adapters.repository.customer;

import com.finance.moneyexchange.domain.customer.port.GetCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GetCustomerRepository implements GetCustomerPort {
    private final CustomerAdapter customerAdapter;

    @Override
    public Optional<CustomerEntity> getCustomer(UUID id) {
        return customerAdapter.findById(id.toString());
    }
}
