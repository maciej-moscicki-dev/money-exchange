package com.finance.moneyexchange.domain.customer.port;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public interface GetCustomerPort {
    Optional<CustomerEntity> getCustomer(UUID id);
}
