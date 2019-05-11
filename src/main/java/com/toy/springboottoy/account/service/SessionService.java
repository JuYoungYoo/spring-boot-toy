package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.dto.SessionDto;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.reepository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final AccountRepository accountRepository;

    public SessionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public SessionDto.SignInRes signIn(SessionDto.SignInReq sessionDto) {
        Optional<SessionDto.SignInRes> findAccount = accountRepository.findByEmailAndPassword(sessionDto.getEmail(), sessionDto.getPassword());
        findAccount.orElseThrow(() -> new AccountNotFoundException(sessionDto.getEmail()));
        // todo : login 처리 로직 security 추가
        return findAccount.get();
    }
}
