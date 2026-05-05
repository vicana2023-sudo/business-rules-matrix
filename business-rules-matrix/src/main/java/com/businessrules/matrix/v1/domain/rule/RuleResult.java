package com.businessrules.matrix.v1.domain.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleResult {
    private boolean approved;
    private String decision;
    private String detail;
    private String ruleApplied;
    private BigDecimal calculatedValue;
}

