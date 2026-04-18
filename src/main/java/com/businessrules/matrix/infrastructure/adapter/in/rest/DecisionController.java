package com.businessrules.matrix.infrastructure.adapter.in.rest;

import com.businessrules.matrix.application.dto.DecisionRequest;
import com.businessrules.matrix.application.dto.DecisionResponse;
import com.businessrules.matrix.application.service.DecisionService;
import com.businessrules.matrix.domain.port.out.AccountRepository;
import com.businessrules.matrix.domain.port.out.CustomerRepository;
import com.businessrules.matrix.domain.rule.RuleEvaluator;
import com.businessrules.matrix.infrastructure.config.RuleProperties;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class DecisionController {

    private final DecisionService decisionService;
    private final RuleEvaluator ruleEvaluator;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @PostMapping("/decisions/evaluate")
    public DecisionResponse evaluate(@Valid @RequestBody DecisionRequest request) {
        log.info("POST /decisions/evaluate");
        return decisionService.evaluate(request);
    }

    @GetMapping("/decisions/rules")
    public RuleProperties listRules() {
        log.info("GET /decisions/rules");
        return ruleEvaluator.getRuleProperties();
    }

    @GetMapping("/customers/{id}")
    public Object customer(@PathVariable Long id) {
        log.info("GET /customers/{}", id);
        return customerRepository.findById(id)
                .<Object>map(value -> value)
                .orElseGet(() -> Map.of("error", "Customer not found"));
    }

    @GetMapping("/accounts/{id}")
    public Object account(@PathVariable Long id) {
        log.info("GET /accounts/{}", id);
        return accountRepository.findById(id)
                .<Object>map(value -> value)
                .orElseGet(() -> Map.of("error", "Account not found"));
    }
}
