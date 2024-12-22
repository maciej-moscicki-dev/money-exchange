package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;
import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.UpdateCustomerPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UpdateCustomerServiceTest {
    @Mock
    private UpdateCustomerPort updateCustomerPort;
    @InjectMocks
    private UpdateCustomerService updateCustomerService;

    @Test
    void shouldUpdateCustomer() {
        Mockito.when(updateCustomerPort.updateCustomer(CUSTOMER_1)).thenReturn(CUSTOMER_ENTITY_1);
        Customer customer = updateCustomerService.updateCustomer(CUSTOMER_1);

        Assertions.assertEquals(CUSTOMER_1, customer);
    }

    private static final Customer CUSTOMER_1 = Customer.builder()
            .uuid(UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea"))
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();

    private static final CustomerEntity CUSTOMER_ENTITY_1 = CustomerEntity.builder()
            .uuid("2ccefa01-4de6-444f-a315-886a74b343ea")
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();
}
