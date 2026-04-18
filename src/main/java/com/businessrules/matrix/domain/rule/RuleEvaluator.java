package com.businessrules.matrix.domain.rule;

import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.infrastructure.config.RuleProperties;
import com.businessrules.matrix.infrastructure.exception.RuleNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RuleEvaluator {

    private final Map<RuleCoordinate, BusinessRule> ruleMatrix;
    private final Map<RuleCoordinate, String> ruleNames;
    @Getter
    private final RuleProperties ruleProperties;

    public RuleEvaluator(RuleProperties properties, RuleFactory factory) {
        this.ruleProperties = properties;
        this.ruleMatrix = buildMatrix(properties, factory);
        this.ruleNames = buildRuleNames(properties);
    }

    public String evaluate(DecisionContext context) {
        RuleCoordinate coordinate = toCoordinate(context);
        BusinessRule rule = ruleMatrix.get(coordinate);
        if (rule == null) {
            throw new RuleNotFoundException("No rule found for coordinate: " + coordinate);
        }
        log.info("Rule selected for coordinate {}", coordinate);
        return rule.execute(context);
    }

    public String ruleApplied(DecisionContext context) {
        return ruleNames.getOrDefault(toCoordinate(context), "UNKNOWN_RULE");
    }

    private RuleCoordinate toCoordinate(DecisionContext context) {
        return RuleCoordinate.builder()
                .customerType(context.getCustomerType())
                .accountStatus(context.getAccountStatus())
                .productType(context.getProductType())
                .build();
    }

    private Map<RuleCoordinate, BusinessRule> buildMatrix(RuleProperties properties, RuleFactory factory) {
        Map<RuleCoordinate, BusinessRule> matrix = new HashMap<>();
        for (RuleProperties.RuleDefinition definition : properties.getRules()) {
            RuleCoordinate coordinate = toCoordinate(definition.getCoordinate());
            matrix.put(coordinate, factory.createRule(definition.getRuleType(), definition.getParameters(), coordinate));
        }
        return Collections.unmodifiableMap(matrix);
    }

    private Map<RuleCoordinate, String> buildRuleNames(RuleProperties properties) {
        Map<RuleCoordinate, String> names = new HashMap<>();
        for (RuleProperties.RuleDefinition definition : properties.getRules()) {
            RuleCoordinate coordinate = toCoordinate(definition.getCoordinate());
            names.put(coordinate, String.join("_", coordinate.getCustomerType(), coordinate.getAccountStatus(), coordinate.getProductType(), definition.getRuleType()));
        }
        return Collections.unmodifiableMap(names);
    }

    private RuleCoordinate toCoordinate(RuleProperties.CoordinateDefinition coordinate) {
        return RuleCoordinate.builder()
                .customerType(coordinate.getCustomerType())
                .accountStatus(coordinate.getAccountStatus())
                .productType(coordinate.getProductType())
                .build();
    }
}
