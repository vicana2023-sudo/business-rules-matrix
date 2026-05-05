package com.businessrules.matrix.v2.domain.model;
import lombok.*;
import java.math.BigDecimal;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DecisionFact {
    private Long customerId;
    private Long accountId;
    private String customerName;
    private String customerType;
    private Integer creditScore;
    private String accountNumber;
    private String accountStatus;
    private BigDecimal balance;
    private BigDecimal debtRatio;
    private String productType;
    private BigDecimal amount;
}
