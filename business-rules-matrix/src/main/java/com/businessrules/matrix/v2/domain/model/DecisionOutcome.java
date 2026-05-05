package com.businessrules.matrix.v2.domain.model;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DecisionOutcome {
    private Boolean approved;
    private String decision;
    private String detail;
    private String ruleApplied;
    private BigDecimal calculatedValue;
    @Builder.Default
    private List<String> branchPath = new ArrayList<>();
    @Builder.Default
    private List<String> firedRules = new ArrayList<>();
    public void addBranchStep(String step) {
        if (branchPath == null) branchPath = new ArrayList<>();
        branchPath.add(step);
    }
    public void addFiredRule(String ruleName) {
        if (firedRules == null) firedRules = new ArrayList<>();
        firedRules.add(ruleName);
    }
}
