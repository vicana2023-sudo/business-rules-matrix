package com.businessrules.matrix.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.infrastructure.config.RuleProperties;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RuleEvaluatorTest {

    @Test
    void shouldMapCoordinatesToConfiguredRules() {
        RuleProperties properties = new RuleProperties();
        properties.getRules().add(rule("VIP", "ACTIVE", "CREDIT", "COMMISSION", Map.of("commission-percentage", 0.5, "max-amount", 10000)));
        properties.getRules().add(rule("REGULAR", "ACTIVE", "SAVINGS", "DISCOUNT", Map.of("discount-percentage", 2.0)));
        properties.getRules().add(rule("NEW", "ACTIVE", "INVESTMENT", "CREDIT_APPROVAL", Map.of("min-score", 700, "max-debt-ratio", 0.4)));
        properties.getRules().add(rule("VIP", "ACTIVE", "INVESTMENT", "PREMIUM_BENEFIT", Map.of("benefit", "Personal Advisor Access", "priority-level", 1)));
        properties.getRules().add(rule("REGULAR", "ACTIVE", "CREDIT", "COMMISSION", Map.of("commission-percentage", 1.5, "max-amount", 5000)));
        properties.getRules().add(rule("VIP", "ACTIVE", "SAVINGS", "DISCOUNT", Map.of("discount-percentage", 5.0)));

        RuleEvaluator evaluator = new RuleEvaluator(properties, new RuleFactory());

        assertThat(evaluator.evaluate(context("VIP", "ACTIVE", "CREDIT", new BigDecimal("1000"), 850, new BigDecimal("0.2"))))
                .contains("Commission applied");
        assertThat(evaluator.evaluate(context("REGULAR", "ACTIVE", "SAVINGS", new BigDecimal("1000"), 720, new BigDecimal("0.2"))))
                .contains("Discount applied");
        assertThat(evaluator.evaluate(context("NEW", "ACTIVE", "INVESTMENT", new BigDecimal("1000"), 750, new BigDecimal("0.2"))))
                .startsWith("APPROVED");
        assertThat(evaluator.evaluate(context("VIP", "ACTIVE", "INVESTMENT", new BigDecimal("1000"), 850, new BigDecimal("0.2"))))
                .contains("Premium benefit");
    }

    private RuleProperties.RuleDefinition rule(String customerType, String accountStatus, String productType, String type, Map<String, Object> params) {
        RuleProperties.RuleDefinition rule = new RuleProperties.RuleDefinition();
        RuleProperties.CoordinateDefinition coordinate = new RuleProperties.CoordinateDefinition();
        coordinate.setCustomerType(customerType);
        coordinate.setAccountStatus(accountStatus);
        coordinate.setProductType(productType);
        rule.setCoordinate(coordinate);
        rule.setRuleType(type);
        rule.setParameters(params);
        return rule;
    }

    private DecisionContext context(String customerType, String accountStatus, String productType, BigDecimal amount, Integer score, BigDecimal debtRatio) {
        return DecisionContext.builder()
                .customerType(customerType)
                .accountStatus(accountStatus)
                .productType(productType)
                .amount(amount)
                .creditScore(score)
                .debtRatio(debtRatio)
                .build();
    }
}
