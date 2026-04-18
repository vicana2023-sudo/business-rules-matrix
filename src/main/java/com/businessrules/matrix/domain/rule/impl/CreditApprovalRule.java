package com.businessrules.matrix.domain.rule.impl;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.BusinessRule;
import com.businessrules.matrix.domain.rule.RuleCoordinate;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreditApprovalRule implements BusinessRule {

    private final RuleCoordinate coordinate;
    private final BigDecimal minScore;
    private final BigDecimal maxDebtRatio;

    @Override
    public String execute(DecisionContext context) {
        boolean approved = BigDecimal.valueOf(context.getCreditScore()).compareTo(minScore) >= 0
                && context.getDebtRatio().compareTo(maxDebtRatio) <= 0;
        if (approved) {
            return "APPROVED: Credit approved with score " + context.getCreditScore();
        }
        return "REJECTED: Credit denied due to score/debt ratio thresholds";
    }

    @Override
    public boolean matches(RuleCoordinate inputCoordinate) {
        return coordinate.equals(inputCoordinate);
    }
}
