package com.businessrules.matrix.domain.rule.impl;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.BusinessRule;
import com.businessrules.matrix.domain.rule.RuleCoordinate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscountRule implements BusinessRule {

    private final RuleCoordinate coordinate;
    private final BigDecimal discountPercentage;

    @Override
    public String execute(DecisionContext context) {
        BigDecimal discount = context.getAmount().multiply(discountPercentage)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return "APPROVED: Discount applied: " + discountPercentage.stripTrailingZeros().toPlainString() + "% ($" + discount + ")";
    }

    @Override
    public boolean matches(RuleCoordinate inputCoordinate) {
        return coordinate.equals(inputCoordinate);
    }
}
