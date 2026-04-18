package com.businessrules.matrix.application.service;

import com.businessrules.matrix.application.dto.DecisionRequest;
import com.businessrules.matrix.application.dto.DecisionResponse;
import com.businessrules.matrix.domain.model.Account;
import com.businessrules.matrix.domain.model.Customer;
import com.businessrules.matrix.domain.model.DecisionContext;
import com.businessrules.matrix.domain.port.in.EvaluateDecisionUseCase;
import com.businessrules.matrix.domain.port.out.AccountRepository;
import com.businessrules.matrix.domain.port.out.CustomerRepository;
import com.businessrules.matrix.domain.rule.RuleEvaluator;
import com.businessrules.matrix.infrastructure.exception.AccountNotFoundException;
import com.businessrules.matrix.infrastructure.exception.CustomerNotFoundException;
import com.businessrules.matrix.infrastructure.exception.InvalidDecisionRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DecisionService implements EvaluateDecisionUseCase {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RuleEvaluator ruleEvaluator;

    @Override
    public DecisionResponse evaluate(DecisionRequest request) {
        log.info("Evaluating decision request customerId={}, accountId={}, productType={}",
                request.getCustomerId(), request.getAccountId(), request.getProductType());

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getAccountId()));

        validateOwnership(customer, account);
        validateActiveAccount(account);

        DecisionContext context = DecisionContext.builder()
                .customerType(customer.getCustomerType())
                .creditScore(customer.getCreditScore())
                .accountStatus(account.getAccountStatus())
                .accountBalance(account.getBalance())
                .debtRatio(account.getDebtRatio())
                .productType(request.getProductType())
                .amount(request.getAmount())
                .build();

        String detail = ruleEvaluator.evaluate(context);
        String decision = detail.startsWith("REJECTED") ? "REJECTED" : "APPROVED";
        String normalizedDetail = detail.substring(detail.indexOf(':') + 2);
        String ruleName = ruleEvaluator.ruleApplied(context);

        log.info("Decision result={}, rule={}", decision, ruleName);

        return DecisionResponse.builder()
                .decision(decision)
                .detail(normalizedDetail)
                .ruleApplied(ruleName)
                .customerType(customer.getCustomerType())
                .accountStatus(account.getAccountStatus())
                .productType(request.getProductType())
                .build();
    }

    private void validateOwnership(Customer customer, Account account) {
        if (!customer.getId().equals(account.getCustomerId())) {
            throw new InvalidDecisionRequestException("Account does not belong to customer");
        }
    }

    private void validateActiveAccount(Account account) {
        if (!"ACTIVE".equals(account.getAccountStatus())) {
            throw new InvalidDecisionRequestException("Account must be ACTIVE");
        }
    }
}
