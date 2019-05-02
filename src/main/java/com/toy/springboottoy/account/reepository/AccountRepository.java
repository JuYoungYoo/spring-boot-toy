package com.toy.springboottoy.account.reepository;

import com.toy.springboottoy.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account, Long>{
    boolean existsByEmail(String email);
}
