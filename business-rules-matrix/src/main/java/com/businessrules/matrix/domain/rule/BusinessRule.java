package com.businessrules.matrix.domain.rule;
import com.businessrules.matrix.domain.model.DecisionContext;
public interface BusinessRule {
    RuleResult execute(DecisionContext context);
    boolean matches(RuleCoordinate coordinate);
}
