package com.businessrules.matrix.v1.domain.port.out;

import com.businessrules.matrix.v1.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(Long id);
}

