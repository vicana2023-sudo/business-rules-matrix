package com.businessrules.matrix.domain.rule;
import com.businessrules.matrix.domain.model.Account.AccountStatus;
import com.businessrules.matrix.domain.model.Customer.CustomerType;
import com.businessrules.matrix.domain.model.DecisionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
@Slf4j
@Component
public class RuleEvaluator {
    private final Map<RuleCoordinate, BusinessRule> rulesMatrix;
    public RuleEvaluator(Map<RuleCoordinate, BusinessRule> rulesMatrix) {
        this.rulesMatrix = rulesMatrix;
    }
    public RuleResult evaluate(DecisionContext context) {
        CustomerType customerType = context.getCustomer().getCustomerType();
        AccountStatus accountStatus = context.getAccount().getAccountStatus();
        String productType = context.getProductType();
        RuleCoordinate coordinate = new RuleCoordinate(customerType, accountStatus, productType);
        log.info("Evaluating rule for coordinate: {}", coordinate);
        BusinessRule rule = rulesMatrix.get(coordinate);
        if (rule == null) {
            log.warn("No rule found for coordinate: {}", coordinate);
            return RuleResult.builder()
                    .approved(false)
                    .decision("REJECTED")
                    .detail("No business rule configured for the given combination of customer type, account status and product type.")
                    .ruleApplied("NONE")
                    .build();
        }
        return rule.execute(context);
    }
    public Map<RuleCoordinate, BusinessRule> getRulesMatrix() {
        return Map.copyOf(rulesMatrix);
    }
}
