package com.businessrules.matrix.v1.infrastructure.adapter.in.rest;
import com.businessrules.matrix.v1.application.dto.DecisionRequest;
import com.businessrules.matrix.v1.application.dto.DecisionResponse;
import com.businessrules.matrix.v1.domain.port.in.EvaluateDecisionUseCase;
import com.businessrules.matrix.v1.domain.rule.BusinessRule;
import com.businessrules.matrix.v1.domain.rule.RuleCoordinate;
import com.businessrules.matrix.v1.domain.rule.RuleEvaluator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/decisions")
@RequiredArgsConstructor
@Tag(name = "v1 - Decision Matrix", description = "Business Rules Decision Matrix API (Map<RuleCoordinate, BusinessRule>)")
public class DecisionController {
    private final EvaluateDecisionUseCase evaluateDecisionUseCase;
    private final RuleEvaluator ruleEvaluator;
    @PostMapping("/evaluate")
    @Operation(summary = "Evaluate a business decision", description = "Applies the configured business rule matrix to a given context")
    public ResponseEntity<DecisionResponse> evaluate(@Valid @RequestBody DecisionRequest request) {
        return ResponseEntity.ok(evaluateDecisionUseCase.evaluate(request));
    }
    @GetMapping("/rules")
    @Operation(summary = "List all configured rules", description = "Returns a summary of all rules loaded in the decision matrix")
    public ResponseEntity<List<Map<String, String>>> listRules() {
        List<Map<String, String>> rules = ruleEvaluator.getRulesMatrix().entrySet().stream()
                .map(entry -> {
                    RuleCoordinate coord = entry.getKey();
                    BusinessRule rule = entry.getValue();
                    return Map.of(
                            "customerType",  coord.getCustomerType().name(),
                            "accountStatus", coord.getAccountStatus().name(),
                            "productType",   coord.getProductType(),
                            "ruleClass",     rule.getClass().getSimpleName()
                    );
                })
                .toList();
        return ResponseEntity.ok(rules);
    }
}
