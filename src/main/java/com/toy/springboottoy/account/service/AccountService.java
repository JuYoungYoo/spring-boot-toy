package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.AccountDto;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.reepository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account signUp(AccountDto.SignUpReq dto) {
        if (accountRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicationException(dto.getEmail());
        }
        return accountRepository.save(dto.toEntity());
    }

    public AccountDto.Res findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        account.orElseThrow(() -> new AccountNotFoundException(id));
        return AccountDto.Res.of(account.get());
    }
}
