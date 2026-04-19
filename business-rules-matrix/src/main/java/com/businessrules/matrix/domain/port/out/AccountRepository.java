package com.businessrules.matrix.domain.port.out;
import com.businessrules.matrix.domain.model.Account;
import java.util.Optional;
public interface AccountRepository {
    Optional<Account> findById(Long id);
}
