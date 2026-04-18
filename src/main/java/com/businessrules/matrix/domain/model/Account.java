package com.businessrules.matrix.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Account {
    Long id;
    String accountNumber;
    Long customerId;
    String accountStatus;
    BigDecimal balance;
    BigDecimal debtRatio;
}
