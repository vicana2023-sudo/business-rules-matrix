package com.businessrules.matrix.domain.port.out;

import com.businessrules.matrix.domain.model.Customer;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(Long id);
}
