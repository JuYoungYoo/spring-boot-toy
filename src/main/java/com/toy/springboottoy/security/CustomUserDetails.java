package com.toy.springboottoy.security;


import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.reepository.AccountRepository;
import com.toy.springboottoy.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init(){
        String username = "juyoung";

        Optional<Account> account = accountRepository.findByEmail(username);
        if(account.isPresent()){

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .name(username)
                    .password("pass")
                    .role(Role.USER)
                    .emailVerified(false)
                    .build();

            Account newAccount = this.signUp(signUpRequest);
            System.out.println(newAccount);
        }
    }

    public Account signUp(SignUpRequest dto) {
        if (accountRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicationException(dto.getEmail());
        }
        dto.encodePassword();
        return accountRepository.save(dto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return UserPrincipal.of(account);
    }
}