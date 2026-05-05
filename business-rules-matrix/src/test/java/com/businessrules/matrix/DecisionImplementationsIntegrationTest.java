package com.businessrules.matrix;
import com.businessrules.matrix.v1.application.dto.DecisionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class DecisionImplementationsIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    // --- V1 · Decision Matrix ------------------------------------------------
    @Test
    void v1_shouldApproveVipLoanWithGoodCreditScore() throws Exception {
        DecisionRequest request = DecisionRequest.builder()
                .customerId(1L).accountId(1L)
                .productType("LOAN").amount(new BigDecimal("10000.00"))
                .build();
        mockMvc.perform(post("/api/v1/decisions/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("APPROVED"))
                .andExpect(jsonPath("$.ruleApplied").value("CREDIT_APPROVAL_RULE"));
    }
    @Test
    void v1_shouldListConfiguredMatrixRules() throws Exception {
        mockMvc.perform(get("/api/v1/decisions/rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.productType=='LOAN')]").exists());
    }
    // --- V2 · Drools Decision Tree -------------------------------------------
    @Test
    void v2_shouldApproveVipLoanWithGoodCreditScore() throws Exception {
        com.businessrules.matrix.v2.application.dto.DecisionRequest request =
                com.businessrules.matrix.v2.application.dto.DecisionRequest.builder()
                        .customerId(1L).accountId(1L)
                        .productType("LOAN").amount(new BigDecimal("10000.00"))
                        .build();
        mockMvc.perform(post("/api/v2/decisions/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("APPROVED"))
                .andExpect(jsonPath("$.ruleApplied").value("TREE_LOAN_VIP_CREDIT_APPROVAL"))
                .andExpect(jsonPath("$.productType").value("LOAN"));
    }
    @Test
    void v2_shouldRejectSuspendedAccountBeforeLeafEvaluation() throws Exception {
        com.businessrules.matrix.v2.application.dto.DecisionRequest request =
                com.businessrules.matrix.v2.application.dto.DecisionRequest.builder()
                        .customerId(5L).accountId(5L)
                        .productType("LOAN").amount(new BigDecimal("1500.00"))
                        .build();
        mockMvc.perform(post("/api/v2/decisions/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("REJECTED"))
                .andExpect(jsonPath("$.ruleApplied").value("TREE_GATE_SUSPENDED_ACCOUNT"));
    }
    @Test
    void v2_shouldFallbackWhenNoLeafConfigured() throws Exception {
        com.businessrules.matrix.v2.application.dto.DecisionRequest request =
                com.businessrules.matrix.v2.application.dto.DecisionRequest.builder()
                        .customerId(3L).accountId(3L)
                        .productType("INSURANCE").amount(new BigDecimal("900.00"))
                        .build();
        mockMvc.perform(post("/api/v2/decisions/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("REJECTED"))
                .andExpect(jsonPath("$.ruleApplied").value("TREE_FALLBACK_NO_RULE_CONFIGURED"));
    }
    @Test
    void v2_shouldExposeRuleCatalogAndTreeDescription() throws Exception {
        mockMvc.perform(get("/api/v2/decisions/rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.ruleName=='TREE_LOAN_VIP_CREDIT_APPROVAL')]").exists());
        mockMvc.perform(get("/api/v2/decisions/tree"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.path=='ROOT -> ACTIVE -> INVESTMENT -> REGULAR')]").exists());
    }
}
