package com.businessrules.matrix.v1.application.service;
import com.businessrules.matrix.v1.application.dto.DecisionRequest;
import com.businessrules.matrix.v1.application.dto.DecisionResponse;
import com.businessrules.matrix.v1.domain.model.Account;
import com.businessrules.matrix.v1.domain.model.Customer;
import com.businessrules.matrix.v1.domain.model.DecisionContext;
import com.businessrules.matrix.v1.domain.port.in.EvaluateDecisionUseCase;
import com.businessrules.matrix.v1.domain.port.out.AccountRepository;
import com.businessrules.matrix.v1.domain.port.out.CustomerRepository;
import com.businessrules.matrix.v1.domain.rule.RuleEvaluator;
import com.businessrules.matrix.v1.domain.rule.RuleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class DecisionService implements EvaluateDecisionUseCase {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RuleEvaluator ruleEvaluator;
    @Override
    public DecisionResponse evaluate(DecisionRequest request) {
        log.info("[v1-Matrix] Evaluating decision for customerId={}, accountId={}, productType={}",
                request.getCustomerId(), request.getAccountId(), request.getProductType());
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer not found with id: " + request.getCustomerId()));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account not found with id: " + request.getAccountId()));
        DecisionContext context = DecisionContext.builder()
                .customer(customer)
                .account(account)
                .productType(request.getProductType().toUpperCase())
                .amount(request.getAmount())
                .build();
        RuleResult result = ruleEvaluator.evaluate(context);
        return DecisionResponse.builder()
                .decision(result.getDecision())
                .detail(result.getDetail())
                .ruleApplied(result.getRuleApplied())
                .customerType(customer.getCustomerType().name())
                .accountStatus(account.getAccountStatus().name())
                .productType(request.getProductType().toUpperCase())
                .calculatedValue(result.getCalculatedValue())
                .build();
    }
}
