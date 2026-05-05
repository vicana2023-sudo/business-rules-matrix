package com.businessrules.matrix.v2.domain.port.out;
import com.businessrules.matrix.v2.domain.model.Customer;
import java.util.Optional;
public interface CustomerRepository {
    Optional<Customer> findById(Long id);
}
