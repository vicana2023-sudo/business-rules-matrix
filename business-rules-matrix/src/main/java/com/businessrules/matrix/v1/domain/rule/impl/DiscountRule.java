package com.businessrules.matrix.v1.domain.rule.impl;
import com.businessrules.matrix.v1.domain.model.DecisionContext;
import com.businessrules.matrix.v1.domain.rule.BusinessRule;
import com.businessrules.matrix.v1.domain.rule.RuleCoordinate;
import com.businessrules.matrix.v1.domain.rule.RuleResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
public class DiscountRule implements BusinessRule {
    private final BigDecimal discountPercentage;
    private final RuleCoordinate coordinate;
    public DiscountRule(Map<String, Object> parameters) {
        this.discountPercentage = new BigDecimal(parameters.get("discountPercentage").toString());
        this.coordinate = null;
    }
    public DiscountRule(Map<String, Object> parameters, RuleCoordinate coordinate) {
        this.discountPercentage = new BigDecimal(parameters.get("discountPercentage").toString());
        this.coordinate = coordinate;
    }
    @Override
    public RuleResult execute(DecisionContext context) {
        BigDecimal discountAmount = context.getAmount()
                .multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal finalAmount = context.getAmount().subtract(discountAmount);
        return RuleResult.builder()
                .approved(true)
                .decision("APPROVED")
                .detail(String.format("Discount of %.2f%% applied. Discount amount: %.2f. Final amount: %.2f",
                        discountPercentage, discountAmount, finalAmount))
                .ruleApplied("DISCOUNT_RULE")
                .calculatedValue(finalAmount)
                .build();
    }
    @Override
    public boolean matches(RuleCoordinate coord) {
        return coordinate != null && coordinate.equals(coord);
    }
}
