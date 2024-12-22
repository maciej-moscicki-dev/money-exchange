package com.finance.moneyexchange.adapters.repository.customer;

import com.finance.moneyexchange.domain.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase()
public class CreateCustomerRepositoryTest {
    @Autowired
    private CustomerAdapter customerAdapter;
    private CreateCustomerRepository createCustomerRepository;

    @BeforeEach
    void init() {
        createCustomerRepository = new CreateCustomerRepository(customerAdapter);
    }

    @Test
    void shouldSaveNewCustomer() {
        createCustomerRepository.createCustomer(CUSTOMER_1);

        assertEquals(1, customerAdapter.count(), "Customer count should be 1");

        CustomerEntity savedCustomer = StreamSupport.stream(customerAdapter.findAll().spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Saved customer not found"));

        assertEquals(CUSTOMER_1.firstName(), savedCustomer.getFirstName());
        assertEquals(CUSTOMER_1.lastName(), savedCustomer.getLastName());
        assertEquals(CUSTOMER_1.balancePln(), savedCustomer.getBalancePln());
        assertEquals(CUSTOMER_1.balanceUsd(), savedCustomer.getBalanceUsd());
        assertNotNull(savedCustomer.getUuid());
    }

    private static final Customer CUSTOMER_1 = Customer.builder()
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();
}
