package com.finance.moneyexchange.adapters.repository.customer;

import com.finance.moneyexchange.domain.customer.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase()
public class UpdateCustomerRepositoryTest {
    @Autowired
    private CustomerAdapter customerAdapter;
    private UpdateCustomerRepository updateCustomerRepository;
    private CreateCustomerRepository createCustomerRepository;

    @BeforeEach
    void init() {
        updateCustomerRepository = new UpdateCustomerRepository(customerAdapter);
        createCustomerRepository = new CreateCustomerRepository(customerAdapter);
    }

    @Test
    void updateCustomer() {
        CustomerEntity customer = createCustomerRepository.createCustomer(CUSTOMER_1);

        assertEquals(1, customerAdapter.count(), "Customer count should be 1");

        CustomerEntity customerEntity = updateCustomerRepository.updateCustomer(CUSTOMER_1.toBuilder()
                .uuid(UUID.fromString(customer.getUuid()))
                .balancePln(new BigDecimal("100"))
                .balanceUsd(new BigDecimal("200"))
                .build());

        Assertions.assertEquals(customerEntity.getUuid(), customer.getUuid());
        Assertions.assertEquals(customerEntity.getFirstName(), customer.getFirstName());
        Assertions.assertEquals(customerEntity.getLastName(), customer.getLastName());
        Assertions.assertEquals(new BigDecimal("100"), customerEntity.getBalancePln());
        Assertions.assertEquals(new BigDecimal("200"), customerEntity.getBalanceUsd());
    }

    private static final Customer CUSTOMER_1 = Customer.builder()
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();
}
