package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.UpdateCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateCustomerService {
    private final UpdateCustomerPort updateCustomerPort;

    public Customer updateCustomer(Customer customer) {
        return CustomerMapper.mapCustomerEntityToCustomer(updateCustomerPort.updateCustomer(customer));
    }
}
