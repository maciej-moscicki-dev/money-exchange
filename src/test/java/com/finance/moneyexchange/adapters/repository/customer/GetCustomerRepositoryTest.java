package com.finance.moneyexchange.adapters.repository.customer;

import com.finance.moneyexchange.domain.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase()
public class GetCustomerRepositoryTest {
    @Autowired
    private CustomerAdapter customerAdapter;
    private CreateCustomerRepository createCustomerRepository;
    private GetCustomerRepository getCustomerRepository;

    @BeforeEach
    void init() {
        createCustomerRepository = new CreateCustomerRepository(customerAdapter);
        getCustomerRepository = new GetCustomerRepository(customerAdapter);
    }

    @Test
    void shouldGetExistingCustomer() {
        createCustomerRepository.createCustomer(CUSTOMER_1);

        assertEquals(1, customerAdapter.count(), "Customer count should be 1");

        CustomerEntity savedCustomer = StreamSupport.stream(customerAdapter.findAll().spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Saved customer not found"));

        CustomerEntity customer = getCustomerRepository.getCustomer(UUID.fromString(savedCustomer.getUuid()))
                .orElseThrow(() -> new AssertionError("Customer not found in database"));

        assertEquals(customer.getUuid(), savedCustomer.getUuid());
        assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customer.getLastName(), savedCustomer.getLastName());
        assertEquals(customer.getBalancePln(), savedCustomer.getBalancePln());
        assertEquals(customer.getBalanceUsd(), savedCustomer.getBalanceUsd());
    }

    private static final Customer CUSTOMER_1 = Customer.builder()
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();
}
