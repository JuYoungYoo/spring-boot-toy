package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.reepository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Account signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        if (existedEmail(email)) {
            throw new EmailDuplicationException(signUpRequest.getEmail());
        }
        signUpRequest.encodePassword(passwordEncoder);
        return accountRepository.save(signUpRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public boolean existedEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Account findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return account;
    }
}
