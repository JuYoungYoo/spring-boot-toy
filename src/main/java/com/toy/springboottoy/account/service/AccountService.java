package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.reepository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account signUp(Account account){
        if(accountRepository.existsByEmail(account.getEmail())){
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        accountRepository.save(account);
        return account;
    }
}
