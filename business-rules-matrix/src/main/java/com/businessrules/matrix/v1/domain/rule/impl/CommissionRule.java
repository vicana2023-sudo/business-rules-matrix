package com.businessrules.matrix.v1.domain.rule.impl;
import com.businessrules.matrix.v1.domain.model.DecisionContext;
import com.businessrules.matrix.v1.domain.rule.BusinessRule;
import com.businessrules.matrix.v1.domain.rule.RuleCoordinate;
import com.businessrules.matrix.v1.domain.rule.RuleResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
public class CommissionRule implements BusinessRule {
    private final BigDecimal commissionPercentage;
    private final RuleCoordinate coordinate;
    public CommissionRule(Map<String, Object> parameters) {
        this.commissionPercentage = new BigDecimal(parameters.get("commissionPercentage").toString());
        this.coordinate = null;
    }
    public CommissionRule(Map<String, Object> parameters, RuleCoordinate coordinate) {
        this.commissionPercentage = new BigDecimal(parameters.get("commissionPercentage").toString());
        this.coordinate = coordinate;
    }
    @Override
    public RuleResult execute(DecisionContext context) {
        BigDecimal commission = context.getAmount()
                .multiply(commissionPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return RuleResult.builder()
                .approved(true)
                .decision("APPROVED")
                .detail(String.format("Commission of %.2f%% applied. Commission amount: %.2f",
                        commissionPercentage, commission))
                .ruleApplied("COMMISSION_RULE")
                .calculatedValue(commission)
                .build();
    }
    @Override
    public boolean matches(RuleCoordinate coord) {
        return coordinate != null && coordinate.equals(coord);
    }
}
