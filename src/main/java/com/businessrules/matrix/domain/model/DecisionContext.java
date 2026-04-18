package com.businessrules.matrix.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DecisionContext {
    String customerType;
    Integer creditScore;
    String accountStatus;
    BigDecimal accountBalance;
    BigDecimal debtRatio;
    String productType;
    BigDecimal amount;
}
