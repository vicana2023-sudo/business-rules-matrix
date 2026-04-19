package com.businessrules.matrix.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.domain.model.Account;
import com.businessrules.matrix.domain.model.Account.AccountStatus;
import com.businessrules.matrix.domain.port.out.AccountRepository;
import com.businessrules.matrix.infrastructure.adapter.out.persistence.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;
    @Override
    public Optional<Account> findById(Long id) {
        return accountJpaRepository.findById(id)
                .map(this::toDomain);
    }
    private Account toDomain(AccountEntity entity) {
        return Account.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .customerId(entity.getCustomerId())
                .accountStatus(AccountStatus.valueOf(entity.getAccountStatus().name()))
                .balance(entity.getBalance())
                .debtRatio(entity.getDebtRatio())
                .build();
    }
}
