package com.finance.moneyexchange.domain.customer.port;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;
import com.finance.moneyexchange.domain.customer.model.Customer;

public interface CreateCustomerPort {
    CustomerEntity createCustomer(Customer customer);
}
