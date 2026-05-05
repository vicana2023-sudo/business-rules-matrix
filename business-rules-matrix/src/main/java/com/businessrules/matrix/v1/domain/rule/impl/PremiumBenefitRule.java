package com.businessrules.matrix.v1.domain.rule.impl;
import com.businessrules.matrix.v1.domain.model.DecisionContext;
import com.businessrules.matrix.v1.domain.rule.BusinessRule;
import com.businessrules.matrix.v1.domain.rule.RuleCoordinate;
import com.businessrules.matrix.v1.domain.rule.RuleResult;
import java.util.Map;
public class PremiumBenefitRule implements BusinessRule {
    private final String benefitMessage;
    private final RuleCoordinate coordinate;
    public PremiumBenefitRule(Map<String, Object> parameters) {
        this.benefitMessage = parameters.getOrDefault("benefitMessage", "Premium benefit applied").toString();
        this.coordinate = null;
    }
    public PremiumBenefitRule(Map<String, Object> parameters, RuleCoordinate coordinate) {
        this.benefitMessage = parameters.getOrDefault("benefitMessage", "Premium benefit applied").toString();
        this.coordinate = coordinate;
    }
    @Override
    public RuleResult execute(DecisionContext context) {
        return RuleResult.builder()
                .approved(true)
                .decision("APPROVED")
                .detail(String.format("Premium benefit for customer %s: %s",
                        context.getCustomer().getName(), benefitMessage))
                .ruleApplied("PREMIUM_BENEFIT_RULE")
                .calculatedValue(context.getAmount())
                .build();
    }
    @Override
    public boolean matches(RuleCoordinate coord) {
        return coordinate != null && coordinate.equals(coord);
    }
}
