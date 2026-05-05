package com.businessrules.matrix.v1.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.v1.infrastructure.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("v1AccountJpaRepository")
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
}

