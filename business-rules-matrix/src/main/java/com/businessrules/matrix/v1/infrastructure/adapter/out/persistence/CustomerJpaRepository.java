package com.businessrules.matrix.v1.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.v1.infrastructure.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("v1CustomerJpaRepository")
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
}

