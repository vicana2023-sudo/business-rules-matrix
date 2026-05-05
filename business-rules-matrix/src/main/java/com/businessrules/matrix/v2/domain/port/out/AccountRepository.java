package com.businessrules.matrix.v2.domain.port.out;
import com.businessrules.matrix.v2.domain.model.Account;
import java.util.Optional;
public interface AccountRepository {
    Optional<Account> findById(Long id);
}
