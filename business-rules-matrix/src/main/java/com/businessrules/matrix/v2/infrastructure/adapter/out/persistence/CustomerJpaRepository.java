package com.businessrules.matrix.v2.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.v2.infrastructure.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("v2CustomerJpaRepository")
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {}

