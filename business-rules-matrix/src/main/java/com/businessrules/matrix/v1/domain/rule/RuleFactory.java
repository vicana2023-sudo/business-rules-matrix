package com.businessrules.matrix.v1.domain.rule;

import com.businessrules.matrix.v1.domain.rule.impl.CommissionRule;
import com.businessrules.matrix.v1.domain.rule.impl.CreditApprovalRule;
import com.businessrules.matrix.v1.domain.rule.impl.DiscountRule;
import com.businessrules.matrix.v1.domain.rule.impl.PremiumBenefitRule;
import com.businessrules.matrix.v1.infrastructure.config.RuleProperties;
import com.businessrules.matrix.v1.infrastructure.config.RuleProperties.RuleDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RuleFactory {
    public BusinessRule createRule(RuleDefinition definition) {
        String ruleType = definition.getRuleType();
        Map<String, Object> params = definition.getParameters();
        log.info("Creating rule of type: {}", ruleType);
        return switch (ruleType) {
            case "COMMISSION"        -> new CommissionRule(params);
            case "DISCOUNT"          -> new DiscountRule(params);
            case "CREDIT_APPROVAL"   -> new CreditApprovalRule(params);
            case "PREMIUM_BENEFIT"   -> new PremiumBenefitRule(params);
            default -> throw new IllegalArgumentException("Unknown rule type: " + ruleType);
        };
    }
}

