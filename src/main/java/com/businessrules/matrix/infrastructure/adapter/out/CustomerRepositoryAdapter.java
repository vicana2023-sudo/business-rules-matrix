package com.businessrules.matrix.infrastructure.adapter.out;

import com.businessrules.matrix.domain.model.Customer;
import com.businessrules.matrix.domain.port.out.CustomerRepository;
import com.businessrules.matrix.infrastructure.adapter.out.persistence.CustomerJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository repository;

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id)
                .map(entity -> Customer.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .customerType(entity.getCustomerType())
                        .creditScore(entity.getCreditScore())
                        .build());
    }
}
