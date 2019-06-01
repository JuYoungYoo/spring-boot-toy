package com.toy.springboottoy.security;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.reepository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        return account;
    }
}