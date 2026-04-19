package com.businessrules.matrix.infrastructure.config;
import com.businessrules.matrix.domain.model.Account.AccountStatus;
import com.businessrules.matrix.domain.model.Customer.CustomerType;
import com.businessrules.matrix.domain.rule.BusinessRule;
import com.businessrules.matrix.domain.rule.RuleCoordinate;
import com.businessrules.matrix.domain.rule.RuleEvaluator;
import com.businessrules.matrix.domain.rule.RuleFactory;
import com.businessrules.matrix.infrastructure.config.RuleProperties.CoordinateDefinition;
import com.businessrules.matrix.infrastructure.config.RuleProperties.RuleDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RuleConfiguration {
    private final RuleProperties ruleProperties;
    private final RuleFactory ruleFactory;
    @Bean
    public Map<RuleCoordinate, BusinessRule> rulesMatrix() {
        Map<RuleCoordinate, BusinessRule> matrix = new HashMap<>();
        for (RuleDefinition definition : ruleProperties.getRules()) {
            CoordinateDefinition coord = definition.getCoordinate();
            RuleCoordinate coordinate = new RuleCoordinate(
                    CustomerType.valueOf(coord.getCustomerType()),
                    AccountStatus.valueOf(coord.getAccountStatus()),
                    coord.getProductType()
            );
            BusinessRule rule = ruleFactory.createRule(definition);
            matrix.put(coordinate, rule);
            log.info("Registered rule [{}] for coordinate {}", definition.getRuleType(), coordinate);
        }
        log.info("Decision matrix initialized with {} rules", matrix.size());
        return matrix;
    }
    @Bean
    public RuleEvaluator ruleEvaluator(Map<RuleCoordinate, BusinessRule> rulesMatrix) {
        return new RuleEvaluator(rulesMatrix);
    }
}
