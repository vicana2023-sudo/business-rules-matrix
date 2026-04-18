package com.businessrules.matrix.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.impl.CommissionRule;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CommissionRuleTest {

    @Test
    void shouldCalculateCommissionUsingConfiguredPercentage() {
        CommissionRule rule = new CommissionRule(
                RuleCoordinate.builder().customerType("VIP").accountStatus("ACTIVE").productType("CREDIT").build(),
                new BigDecimal("0.5"),
                new BigDecimal("10000"));

        String result = rule.execute(DecisionContext.builder().amount(new BigDecimal("5000")).build());

        assertThat(result).contains("0.5%").contains("$25.00");
    }

    @Test
    void shouldApplyMaxAmountCap() {
        CommissionRule rule = new CommissionRule(
                RuleCoordinate.builder().customerType("VIP").accountStatus("ACTIVE").productType("CREDIT").build(),
                new BigDecimal("1.5"),
                new BigDecimal("5000"));

        String result = rule.execute(DecisionContext.builder().amount(new BigDecimal("10000")).build());

        assertThat(result).contains("$75.00");
    }
}
