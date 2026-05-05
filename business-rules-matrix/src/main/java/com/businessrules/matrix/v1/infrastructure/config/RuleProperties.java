package com.businessrules.matrix.v1.infrastructure.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Component
@ConfigurationProperties(prefix = "decision")
public class RuleProperties {
    private List<RuleDefinition> rules = new ArrayList<>();
    @Data
    public static class RuleDefinition {
        private CoordinateDefinition coordinate;
        private String ruleType;
        private Map<String, Object> parameters = new HashMap<>();
    }
    @Data
    public static class CoordinateDefinition {
        private String customerType;
        private String accountStatus;
        private String productType;
    }
}
