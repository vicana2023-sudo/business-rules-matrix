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
public class DecisionContext {
    private Customer customer;
    private Account account;
    private String productType;
    private BigDecimal amount;
}

