package com.businessrules.matrix.domain.rule.impl;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.BusinessRule;
import com.businessrules.matrix.domain.rule.RuleCoordinate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommissionRule implements BusinessRule {

    private final RuleCoordinate coordinate;
    private final BigDecimal commissionPercentage;
    private final BigDecimal maxAmount;

    @Override
    public String execute(DecisionContext context) {
        BigDecimal amount = context.getAmount().min(maxAmount);
        BigDecimal commission = amount.multiply(commissionPercentage)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return "APPROVED: Commission applied: " + commissionPercentage.stripTrailingZeros().toPlainString() + "% ($" + commission + ")";
    }

    @Override
    public boolean matches(RuleCoordinate inputCoordinate) {
        return coordinate.equals(inputCoordinate);
    }
}
