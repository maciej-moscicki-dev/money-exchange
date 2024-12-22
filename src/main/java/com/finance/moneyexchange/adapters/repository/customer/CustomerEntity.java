package com.finance.moneyexchange.adapters.repository.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "CUSTOMER")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CustomerEntity {
    @Id
    private String uuid;
    @NonNull
    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    private String firstName;
    @NonNull
    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;
    @NonNull
    @Column(name = "BALANCE_PLN", nullable = false)
    private BigDecimal balancePln;
    @NonNull
    @Column(name = "BALANCE_USD", nullable = false)
    private BigDecimal balanceUsd;
}
