package com.toy.springboottoy.account;

import com.toy.springboottoy.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, Long>{

    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);
}
