package com.businessrules.matrix.v2.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.v2.infrastructure.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("v2AccountJpaRepository")
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {}

