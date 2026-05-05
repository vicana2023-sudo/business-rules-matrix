package com.businessrules.matrix.v2.infrastructure.adapter.in.rest;
import com.businessrules.matrix.v2.application.dto.DecisionRequest;
import com.businessrules.matrix.v2.application.dto.DecisionResponse;
import com.businessrules.matrix.v2.application.dto.DecisionTreePathDescriptor;
import com.businessrules.matrix.v2.application.dto.DroolsRuleDescriptor;
import com.businessrules.matrix.v2.application.service.DroolsDecisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v2/decisions")
@RequiredArgsConstructor
@Tag(name = "v2 - Decision Tree (Drools)", description = "Drools Decision Tree API - reglas evaluadas mediante KieSession")
public class DroolsDecisionController {
    private final DroolsDecisionService droolsDecisionService;
    @PostMapping("/evaluate")
    @Operation(summary = "Evaluate a business decision with Drools", description = "Applies the Drools decision tree to the given context")
    public ResponseEntity<DecisionResponse> evaluate(@Valid @RequestBody DecisionRequest request) {
        return ResponseEntity.ok(droolsDecisionService.evaluate(request));
    }
    @GetMapping("/rules")
    @Operation(summary = "List loaded Drools rules", description = "Returns all rule names loaded into the Drools knowledge base")
    public ResponseEntity<List<DroolsRuleDescriptor>> listRules() {
        return ResponseEntity.ok(droolsDecisionService.listRules());
    }
    @GetMapping("/tree")
    @Operation(summary = "Describe the decision tree", description = "Returns the configured business paths represented by the Drools decision tree")
    public ResponseEntity<List<DecisionTreePathDescriptor>> describeTree() {
        return ResponseEntity.ok(droolsDecisionService.describeTree());
    }
}
