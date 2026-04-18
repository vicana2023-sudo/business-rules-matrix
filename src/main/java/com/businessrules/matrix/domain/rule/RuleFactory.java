package com.businessrules.matrix.domain.rule;

import com.businessrules.matrix.domain.model.RuleType;
import com.businessrules.matrix.domain.rule.impl.CommissionRule;
import com.businessrules.matrix.domain.rule.impl.CreditApprovalRule;
import com.businessrules.matrix.domain.rule.impl.DiscountRule;
import com.businessrules.matrix.domain.rule.impl.PremiumBenefitRule;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RuleFactory {

    public BusinessRule createRule(String ruleType, Map<String, Object> parameters, RuleCoordinate coordinate) {
        RuleType type = RuleType.valueOf(ruleType);
        return switch (type) {
            case COMMISSION -> new CommissionRule(
                    coordinate,
                    toBigDecimal(parameters.get("commission-percentage")),
                    toBigDecimal(parameters.get("max-amount")));
            case DISCOUNT -> new DiscountRule(coordinate, toBigDecimal(parameters.get("discount-percentage")));
            case CREDIT_APPROVAL -> new CreditApprovalRule(
                    coordinate,
                    toBigDecimal(parameters.get("min-score")),
                    toBigDecimal(parameters.get("max-debt-ratio")));
            case PREMIUM_BENEFIT -> new PremiumBenefitRule(
                    coordinate,
                    String.valueOf(parameters.get("benefit")),
                    toInteger(parameters.get("priority-level")));
        };
    }

    private BigDecimal toBigDecimal(Object value) {
        return new BigDecimal(String.valueOf(value));
    }

    private Integer toInteger(Object value) {
        return Integer.valueOf(String.valueOf(value));
    }
}
