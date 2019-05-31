package com.toy.springboottoy.common;

import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.service.AccountService;
import com.toy.springboottoy.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitComponent implements ApplicationRunner {

    private final AppProperties appProperties;
    private final AccountService accountService;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        setFixtureAccount("manager", appProperties.getUserId(), appProperties.getUserPassword(), RoleType.MANAGER);
    }

    private void setFixtureAccount(String name,
                                   String email,
                                   String password,
                                   RoleType roleType) {
        SignUpRequest account = SignUpRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .roleType(roleType)
                .emailVerified(true)
                .build();
        try {
            customUserDetailsService.loadUserByUsername(email);
        } catch (Exception e) {
            accountService.signUp(account);
        }
    }
}
