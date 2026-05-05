package com.businessrules.matrix.v2.infrastructure.adapter.out.persistence;
import com.businessrules.matrix.v2.domain.model.Customer;
import com.businessrules.matrix.v2.domain.model.Customer.CustomerType;
import com.businessrules.matrix.v2.domain.port.out.CustomerRepository;
import com.businessrules.matrix.v2.infrastructure.adapter.out.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component("v2CustomerRepositoryAdapter")
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id).map(this::toDomain);
    }
    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .customerType(CustomerType.valueOf(entity.getCustomerType().name()))
                .creditScore(entity.getCreditScore())
                .build();
    }
}

