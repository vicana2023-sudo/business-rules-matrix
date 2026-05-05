package com.businessrules.matrix.v2.application.service;
import com.businessrules.matrix.v2.application.dto.DecisionRequest;
import com.businessrules.matrix.v2.application.dto.DecisionResponse;
import com.businessrules.matrix.v2.application.dto.DecisionTreePathDescriptor;
import com.businessrules.matrix.v2.application.dto.DroolsRuleDescriptor;
import com.businessrules.matrix.v2.domain.model.Account;
import com.businessrules.matrix.v2.domain.model.Customer;
import com.businessrules.matrix.v2.domain.model.DecisionFact;
import com.businessrules.matrix.v2.domain.model.DecisionOutcome;
import com.businessrules.matrix.v2.domain.port.in.EvaluateDecisionUseCase;
import com.businessrules.matrix.v2.domain.port.out.AccountRepository;
import com.businessrules.matrix.v2.domain.port.out.CustomerRepository;
import com.businessrules.matrix.v2.infrastructure.engine.DroolsDecisionEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class DroolsDecisionService implements EvaluateDecisionUseCase {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final DroolsDecisionEngine droolsDecisionEngine;
    @Override
    public DecisionResponse evaluate(DecisionRequest request) {
        log.info("[v2-Drools] Evaluating decision tree for customerId={}, accountId={}, productType={}",
                request.getCustomerId(), request.getAccountId(), request.getProductType());
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer not found with id: " + request.getCustomerId()));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account not found with id: " + request.getAccountId()));
        String normalizedProductType = request.getProductType().toUpperCase();
        DecisionFact fact = DecisionFact.builder()
                .customerId(customer.getId())
                .accountId(account.getId())
                .customerName(customer.getName())
                .customerType(customer.getCustomerType().name())
                .creditScore(customer.getCreditScore())
                .accountNumber(account.getAccountNumber())
                .accountStatus(account.getAccountStatus().name())
                .balance(account.getBalance())
                .debtRatio(account.getDebtRatio())
                .productType(normalizedProductType)
                .amount(request.getAmount())
                .build();
        DecisionOutcome outcome = droolsDecisionEngine.evaluate(fact);
        return DecisionResponse.builder()
                .decision(outcome.getDecision())
                .detail(outcome.getDetail())
                .ruleApplied(outcome.getRuleApplied())
                .customerType(customer.getCustomerType().name())
                .accountStatus(account.getAccountStatus().name())
                .productType(normalizedProductType)
                .calculatedValue(outcome.getCalculatedValue())
                .build();
    }
    public List<DroolsRuleDescriptor> listRules() {
        return droolsDecisionEngine.listRules();
    }
    public List<DecisionTreePathDescriptor> describeTree() {
        return List.of(
            new DecisionTreePathDescriptor("ROOT -> ACCOUNT_STATUS:SUSPENDED", "REJECTED",
                    "Cualquier cuenta suspendida se rechaza antes de evaluar el producto."),
            new DecisionTreePathDescriptor("ROOT -> ACCOUNT_STATUS:CLOSED", "REJECTED",
                    "Cualquier cuenta cerrada se rechaza antes de evaluar el producto."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> LOAN -> VIP", "CREDIT_APPROVAL",
                    "Aprueba o rechaza segun creditScore >= 600 y debtRatio <= 0.40."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> LOAN -> REGULAR", "CREDIT_APPROVAL",
                    "Aprueba o rechaza segun creditScore >= 700 y debtRatio <= 0.35."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> LOAN -> NEW", "CREDIT_APPROVAL",
                    "Aprueba o rechaza segun creditScore >= 750 y debtRatio <= 0.30."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> INVESTMENT -> VIP", "PREMIUM_BENEFIT",
                    "Entrega beneficio premium para clientes VIP."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> INVESTMENT -> REGULAR", "DISCOUNT",
                    "Aplica descuento del 10%% sobre el monto."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> INVESTMENT -> NEW", "COMMISSION",
                    "Aplica comision del 2.5%% sobre el monto."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> INSURANCE -> VIP", "DISCOUNT",
                    "Aplica descuento del 20%% sobre el monto."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> INSURANCE -> REGULAR", "COMMISSION",
                    "Aplica comision del 5.0%% sobre el monto."),
            new DecisionTreePathDescriptor("ROOT -> ACTIVE -> [COMBINATION NOT CONFIGURED]", "REJECTED",
                    "Si no existe una hoja del arbol para la combinacion recibida, se rechaza por fallback.")
        );
    }
}
