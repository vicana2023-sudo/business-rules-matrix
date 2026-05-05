package com.businessrules.matrix.v1.domain.port.out;

import com.businessrules.matrix.v1.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
}

