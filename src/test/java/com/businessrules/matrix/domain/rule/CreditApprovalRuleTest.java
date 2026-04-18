package com.businessrules.matrix.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.impl.CreditApprovalRule;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CreditApprovalRuleTest {

    @Test
    void shouldApproveWhenScoreAndDebtRatioAreWithinThreshold() {
        CreditApprovalRule rule = new CreditApprovalRule(
                RuleCoordinate.builder().customerType("NEW").accountStatus("ACTIVE").productType("INVESTMENT").build(),
                new BigDecimal("700"),
                new BigDecimal("0.4"));

        String result = rule.execute(DecisionContext.builder()
                .creditScore(720)
                .debtRatio(new BigDecimal("0.30"))
                .build());

        assertThat(result).startsWith("APPROVED");
    }

    @Test
    void shouldRejectWhenDebtRatioIsAboveThreshold() {
        CreditApprovalRule rule = new CreditApprovalRule(
                RuleCoordinate.builder().customerType("NEW").accountStatus("ACTIVE").productType("INVESTMENT").build(),
                new BigDecimal("700"),
                new BigDecimal("0.4"));

        String result = rule.execute(DecisionContext.builder()
                .creditScore(730)
                .debtRatio(new BigDecimal("0.45"))
                .build());

        assertThat(result).startsWith("REJECTED");
    }
}
