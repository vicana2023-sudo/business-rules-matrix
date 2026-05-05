package com.businessrules.matrix.v2.infrastructure.config;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.util.List;
import java.util.stream.Collectors;
@Configuration
@RequiredArgsConstructor
public class DroolsConfiguration {
    private final DroolsRuleProperties droolsRuleProperties;
    @Bean
    public KieBase droolsDecisionTreeKieBase() {
        List<String> ruleFiles = droolsRuleProperties.getRuleFiles();
        if (ruleFiles == null || ruleFiles.isEmpty()) {
            throw new IllegalStateException("No DRL files configured under decision-tree.rule-files");
        }
        KieHelper kieHelper = new KieHelper();
        for (String ruleFile : ruleFiles) {
            ClassPathResource resource = new ClassPathResource(ruleFile);
            if (!resource.exists()) {
                throw new IllegalStateException("Drools rule file not found on classpath: " + ruleFile);
            }
            kieHelper.addResource(ResourceFactory.newClassPathResource(ruleFile), ResourceType.DRL);
        }
        Results verification = kieHelper.verify();
        if (verification.hasMessages(Message.Level.ERROR)) {
            String errorMsg = verification.getMessages(Message.Level.ERROR).stream()
                    .map(Message::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            throw new IllegalStateException("Drools verification failed:" + System.lineSeparator() + errorMsg);
        }
        return kieHelper.build();
    }
}
