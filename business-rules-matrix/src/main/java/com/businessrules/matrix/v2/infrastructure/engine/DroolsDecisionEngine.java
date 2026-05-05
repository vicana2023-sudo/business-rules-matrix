package com.businessrules.matrix.v2.infrastructure.engine;
import com.businessrules.matrix.v2.application.dto.DroolsRuleDescriptor;
import com.businessrules.matrix.v2.domain.model.DecisionFact;
import com.businessrules.matrix.v2.domain.model.DecisionOutcome;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
@Component
@RequiredArgsConstructor
public class DroolsDecisionEngine {
    private final KieBase droolsDecisionTreeKieBase;
    public DecisionOutcome evaluate(DecisionFact fact) {
        DecisionOutcome outcome = DecisionOutcome.builder().build();
        KieSession kieSession = droolsDecisionTreeKieBase.newKieSession();
        try {
            kieSession.addEventListener(new DefaultAgendaEventListener() {
                @Override
                public void afterMatchFired(AfterMatchFiredEvent event) {
                    outcome.addFiredRule(event.getMatch().getRule().getName());
                }
            });
            kieSession.insert(fact);
            kieSession.insert(outcome);
            kieSession.fireAllRules();
            if (outcome.getCalculatedValue() == null) {
                outcome.setCalculatedValue(fact.getAmount());
            }
            return outcome;
        } finally {
            kieSession.dispose();
        }
    }
    public List<DroolsRuleDescriptor> listRules() {
        return droolsDecisionTreeKieBase.getKiePackages().stream()
                .sorted(Comparator.comparing(KiePackage::getName))
                .flatMap(pkg -> pkg.getRules().stream()
                        .sorted(Comparator.comparing(Rule::getName))
                        .map(rule -> new DroolsRuleDescriptor(pkg.getName(), rule.getName())))
                .toList();
    }
}
