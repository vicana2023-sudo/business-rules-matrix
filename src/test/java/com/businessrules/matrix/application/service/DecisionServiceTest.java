package com.businessrules.matrix.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.businessrules.matrix.application.dto.DecisionRequest;
import com.businessrules.matrix.application.dto.DecisionResponse;
import com.businessrules.matrix.BusinessRulesMatrixApplication;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BusinessRulesMatrixApplication.class)
class DecisionServiceTest {

    @Autowired
    private DecisionService decisionService;

    @Test
    void shouldEvaluateVipCreditDecisionEndToEnd() {
        DecisionRequest request = new DecisionRequest();
        request.setCustomerId(1L);
        request.setAccountId(100L);
        request.setProductType("CREDIT");
        request.setAmount(new BigDecimal("5000"));

        DecisionResponse response = decisionService.evaluate(request);

        assertThat(response.getDecision()).isEqualTo("APPROVED");
        assertThat(response.getRuleApplied()).isEqualTo("VIP_ACTIVE_CREDIT_COMMISSION");
    }
}
