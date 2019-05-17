package com.toy.springboottoy.account.reepository;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.SessionDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, Long>{

    Optional<SessionDto.SignInRes> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
