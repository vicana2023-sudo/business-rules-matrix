package com.businessrules.matrix.infrastructure.adapter.in.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.businessrules.matrix.BusinessRulesMatrixApplication;
import com.businessrules.matrix.application.dto.DecisionRequest;
import com.businessrules.matrix.application.dto.DecisionResponse;
import com.businessrules.matrix.infrastructure.exception.CustomerNotFoundException;
import com.businessrules.matrix.infrastructure.config.RuleProperties;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BusinessRulesMatrixApplication.class)
class DecisionControllerTest {

    @Autowired
    private DecisionController controller;

    @Test
    void shouldEvaluateDecisionViaController() {
        DecisionRequest request = new DecisionRequest();
        request.setCustomerId(1L);
        request.setAccountId(100L);
        request.setProductType("CREDIT");
        request.setAmount(new BigDecimal("5000"));

        DecisionResponse response = controller.evaluate(request);

        assertThat(response.getDecision()).isEqualTo("APPROVED");
        assertThat(response.getDetail()).contains("Commission applied");
    }

    @Test
    void shouldReturnConfiguredRules() {
        RuleProperties rules = controller.listRules();

        assertThat(rules.getRules()).hasSize(6);
    }

    @Test
    void shouldThrowWhenCustomerDoesNotExist() {
        assertThatThrownBy(() -> controller.customer(999L))
                .isInstanceOf(CustomerNotFoundException.class);
    }
}
