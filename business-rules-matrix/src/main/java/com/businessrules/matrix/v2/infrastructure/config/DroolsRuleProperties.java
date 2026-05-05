package com.businessrules.matrix.v2.infrastructure.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Data
@Component
@ConfigurationProperties(prefix = "decision-tree")
public class DroolsRuleProperties {
    private List<String> ruleFiles = new ArrayList<>();
}
