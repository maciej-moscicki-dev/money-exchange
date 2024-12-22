package com.finance.moneyexchange.adapters.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.moneyexchange.domain.customer.model.Customer;
import com.finance.moneyexchange.domain.customer.service.CreateCustomerService;
import com.finance.moneyexchange.domain.customer.service.GetCustomerService;
import com.finance.moneyexchange.dto.customer.CustomerDto;
import com.finance.moneyexchange.dto.customer.NewCustomerDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @MockitoBean
    private CreateCustomerService createCustomerService;
    @MockitoBean
    private GetCustomerService getCustomerService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateNewCustomer() throws Exception {
        Mockito.when(createCustomerService.createCustomer(NEW_CUSTOMER_DTO_1)).thenReturn(CUSTOMER_1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(NEW_CUSTOMER_DTO_1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(CUSTOMER_DTO_1)));
    }

    @Test
    void shouldGetCustomerByUUID() throws Exception {
        UUID uuid = UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea");
        Mockito.when(getCustomerService.getCustomer(uuid)).thenReturn(CUSTOMER_1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/customer/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(CUSTOMER_DTO_1)));
    }

    private static final Customer CUSTOMER_1 = Customer.builder()
            .uuid(UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea"))
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();

    private static final CustomerDto CUSTOMER_DTO_1 = CustomerDto.builder()
            .uuid(UUID.fromString("2ccefa01-4de6-444f-a315-886a74b343ea"))
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .balanceUsd(BigDecimal.ZERO)
            .build();

    private static final NewCustomerDto NEW_CUSTOMER_DTO_1 = NewCustomerDto.builder()
            .firstName("Imie")
            .lastName("Nazwisko")
            .balancePln(new BigDecimal("10"))
            .build();
}
