package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;
import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.GetCustomerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GetCustomerServiceTest {
    @Mock
    private GetCustomerPort getCustomerPort;
    @InjectMocks
    private GetCustomerService getCustomerService;

    @Test
    void shouldGetCustomer() {
        UUID uuid = UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea");
        Mockito.when(getCustomerPort.getCustomer(uuid)).thenReturn(Optional.of(CUSTOMER_ENTITY_1));

        Customer customer = getCustomerService.getCustomer(uuid);

        assertEquals(customer.uuid().toString(), CUSTOMER_ENTITY_1.getUuid());
        assertEquals(customer.firstName(), CUSTOMER_ENTITY_1.getFirstName());
        assertEquals(customer.lastName(), CUSTOMER_ENTITY_1.getLastName());
        assertEquals(customer.balancePln(), CUSTOMER_ENTITY_1.getBalancePln());
        assertEquals(customer.balanceUsd(), CUSTOMER_ENTITY_1.getBalanceUsd());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotInDb() {
        UUID uuid = UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea");
        Mockito.when(getCustomerPort.getCustomer(uuid)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> getCustomerService.getCustomer(uuid)
        );

        assertEquals("Customer with UUID: 2ccefa01-4de6-444f-a315-886a74b343ea not found.", exception.getMessage());
    }

    private static final CustomerEntity CUSTOMER_ENTITY_1 = CustomerEntity.builder()
            .uuid("2ccefa01-4de6-444f-a315-886a74b343ea")
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();
}
