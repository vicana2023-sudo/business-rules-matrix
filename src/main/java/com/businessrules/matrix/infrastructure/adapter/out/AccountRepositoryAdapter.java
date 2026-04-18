package com.businessrules.matrix.infrastructure.adapter.out;

import com.businessrules.matrix.domain.model.Account;
import com.businessrules.matrix.domain.port.out.AccountRepository;
import com.businessrules.matrix.infrastructure.adapter.out.persistence.AccountJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountJpaRepository repository;

    @Override
    public Optional<Account> findById(Long id) {
        return repository.findById(id)
                .map(entity -> Account.builder()
                        .id(entity.getId())
                        .accountNumber(entity.getAccountNumber())
                        .customerId(entity.getCustomerId())
                        .accountStatus(entity.getAccountStatus())
                        .balance(entity.getBalance())
                        .debtRatio(entity.getDebtRatio())
                        .build());
    }
}
