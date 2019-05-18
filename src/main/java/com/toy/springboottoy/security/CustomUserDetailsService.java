package com.toy.springboottoy.security;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.reepository.AccountRepository;
import com.toy.springboottoy.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account signUp(SignUpRequest signUpRequest) {
        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailDuplicationException(signUpRequest.getEmail());
        }
        signUpRequest.encodePassword(passwordEncoder);
        return accountRepository.save(signUpRequest.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(username));
        return UserPrincipal.of(account);
    }

    public UserDetails loadUserByUserId(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return UserPrincipal.of(account);
    }
}