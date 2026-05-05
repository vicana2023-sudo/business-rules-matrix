package com.businessrules.matrix.v1.domain.rule;

import com.businessrules.matrix.v1.domain.model.DecisionContext;

public interface BusinessRule {
    RuleResult execute(DecisionContext context);
    boolean matches(RuleCoordinate coordinate);
}

