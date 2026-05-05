package com.businessrules.matrix.v2.domain.model;
import lombok.*;
import java.math.BigDecimal;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Account {
    private Long id;
    private String accountNumber;
    private Long customerId;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private BigDecimal debtRatio;
    public enum AccountStatus { ACTIVE, SUSPENDED, CLOSED }
}
