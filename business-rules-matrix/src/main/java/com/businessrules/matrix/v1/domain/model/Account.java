package com.businessrules.matrix.v1.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String accountNumber;
    private Long customerId;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private BigDecimal debtRatio;

    public enum AccountStatus {
        ACTIVE, SUSPENDED, CLOSED
    }
}

