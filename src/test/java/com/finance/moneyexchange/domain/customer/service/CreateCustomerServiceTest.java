package com.finance.moneyexchange.domain.customer.service;

import com.finance.moneyexchange.adapters.repository.customer.CustomerEntity;
import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.port.CreateCustomerPort;
import com.finance.moneyexchange.dto.customer.NewCustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerServiceTest {
    @Mock
    private CreateCustomerPort createCustomerPort;
    @InjectMocks
    private CreateCustomerService createCustomerService;

    @Test
    void shouldCreateCustomer() {
        Mockito.when(createCustomerPort.createCustomer(any())).thenReturn(CUSTOMER_ENTITY_1);
        Customer customer = createCustomerService.createCustomer(NEW_CUSTOMER_DTO_1);

        assertEquals(customer.uuid().toString(), CUSTOMER_ENTITY_1.getUuid());
        assertEquals(customer.firstName(), CUSTOMER_ENTITY_1.getFirstName());
        assertEquals(customer.lastName(), CUSTOMER_ENTITY_1.getLastName());
        assertEquals(customer.balancePln(), CUSTOMER_ENTITY_1.getBalancePln());
        assertEquals(customer.balanceUsd(), CUSTOMER_ENTITY_1.getBalanceUsd());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCustomerData")
    void validateNewCustomerInput_throwsException(NewCustomerDto customerDto, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCustomerService.createCustomer(customerDto)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Object[]> provideInvalidCustomerData() {
        return Stream.of(
                new Object[]{new NewCustomerDto(null, "Doe", BigDecimal.valueOf(100.00)), "Field firstName is required"},
                new Object[]{new NewCustomerDto("John", null, BigDecimal.valueOf(100.00)), "Field lastName is required"},
                new Object[]{new NewCustomerDto("John", "Doe", null), "Field balancePln is required"},
                new Object[]{new NewCustomerDto("John", "Doe", BigDecimal.valueOf(-10.00)), "Field balancePln must be greater than zero"},
                new Object[]{new NewCustomerDto("John", "Doe", BigDecimal.valueOf(0.00)), "Field balancePln must be greater than zero"}
        );
    }

    private static final NewCustomerDto NEW_CUSTOMER_DTO_1 = NewCustomerDto.builder()
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .build();

    private static final CustomerEntity CUSTOMER_ENTITY_1 = CustomerEntity.builder()
            .uuid("2ccefa01-4de6-444f-a315-886a74b343ea")
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();
}
