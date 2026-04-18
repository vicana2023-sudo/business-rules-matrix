package com.businessrules.matrix.infrastructure.adapter.out.persistence;

import com.businessrules.matrix.infrastructure.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
}
