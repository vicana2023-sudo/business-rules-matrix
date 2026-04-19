package com.businessrules.matrix.domain.rule.impl;
import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.rule.BusinessRule;
import com.businessrules.matrix.domain.rule.RuleCoordinate;
import com.businessrules.matrix.domain.rule.RuleResult;
import java.math.BigDecimal;
import java.util.Map;
public class CreditApprovalRule implements BusinessRule {
    private final int minCreditScore;
    private final BigDecimal maxDebtRatio;
    private final RuleCoordinate coordinate;
    public CreditApprovalRule(Map<String, Object> parameters) {
        this.minCreditScore = Integer.parseInt(parameters.get("minCreditScore").toString());
        this.maxDebtRatio = new BigDecimal(parameters.get("maxDebtRatio").toString());
        this.coordinate = null;
    }
    public CreditApprovalRule(Map<String, Object> parameters, RuleCoordinate coordinate) {
        this.minCreditScore = Integer.parseInt(parameters.get("minCreditScore").toString());
        this.maxDebtRatio = new BigDecimal(parameters.get("maxDebtRatio").toString());
        this.coordinate = coordinate;
    }
    @Override
    public RuleResult execute(DecisionContext context) {
        int creditScore = context.getCustomer().getCreditScore();
        BigDecimal debtRatio = context.getAccount().getDebtRatio();
        boolean creditScoreOk = creditScore >= minCreditScore;
        boolean debtRatioOk = debtRatio.compareTo(maxDebtRatio) <= 0;
        boolean approved = creditScoreOk && debtRatioOk;
        String detail;
        if (approved) {
            detail = String.format("Credit approved. Score: %d (min: %d), Debt ratio: %.2f (max: %.2f)",
                    creditScore, minCreditScore, debtRatio, maxDebtRatio);
        } else {
            StringBuilder reasons = new StringBuilder("Credit rejected. Reasons: ");
            if (!creditScoreOk) reasons.append(String.format("Credit score %d below minimum %d. ", creditScore, minCreditScore));
            if (!debtRatioOk) reasons.append(String.format("Debt ratio %.2f exceeds maximum %.2f.", debtRatio, maxDebtRatio));
            detail = reasons.toString();
        }
        return RuleResult.builder()
                .approved(approved)
                .decision(approved ? "APPROVED" : "REJECTED")
                .detail(detail)
                .ruleApplied("CREDIT_APPROVAL_RULE")
                .calculatedValue(context.getAmount())
                .build();
    }
    @Override
    public boolean matches(RuleCoordinate coord) {
        return coordinate != null && coordinate.equals(coord);
    }
}
