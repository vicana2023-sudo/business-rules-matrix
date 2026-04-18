package com.businessrules.matrix.domain.rule.impl;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.BusinessRule;
import com.businessrules.matrix.domain.rule.RuleCoordinate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PremiumBenefitRule implements BusinessRule {

    private final RuleCoordinate coordinate;
    private final String benefit;
    private final String priorityLevel;

    @Override
    public String execute(DecisionContext context) {
        return "APPROVED: Premium benefit granted: " + benefit + " (priority " + priorityLevel + ")";
    }

    @Override
    public boolean matches(RuleCoordinate inputCoordinate) {
        return coordinate.equals(inputCoordinate);
    }
}
