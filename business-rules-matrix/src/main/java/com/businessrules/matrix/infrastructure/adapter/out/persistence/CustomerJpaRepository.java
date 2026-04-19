package com.businessrules.matrix.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.infrastructure.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
}
