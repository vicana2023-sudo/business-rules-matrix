package com.businessrules.matrix.infrastructure.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "decision")
@Data
public class RuleProperties {

    private List<RuleDefinition> rules = new ArrayList<>();

    @Data
    public static class RuleDefinition {
        private CoordinateDefinition coordinate = new CoordinateDefinition();
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
