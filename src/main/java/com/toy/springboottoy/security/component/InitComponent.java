package com.toy.springboottoy.security.component;


import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.config.AppProperties;
import com.toy.springboottoy.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InitComponent implements ApplicationRunner {

    private final AppProperties appProperties;
    private final CustomUserDetailsService customUserDetailsService;

    public InitComponent(AppProperties appProperties,
                         CustomUserDetailsService customUserDetailsService) {
        this.appProperties = appProperties;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        setFixtureAccount("admin", appProperties.getAdminId(), appProperties.getAdminPassword(), Role.MANAGER);
        setFixtureAccount("user", appProperties.getUserId(), appProperties.getUserPassword(), Role.USER);
    }

    private void setFixtureAccount(String name,
                                   String email,
                                   String password,
                                   Role role) {
        SignUpRequest account = SignUpRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .emailVerified(true)
                .build();
        try {
            customUserDetailsService.loadUserByUsername(email);
        } catch (Exception e) {
            customUserDetailsService.signUp(account);
        }
    }
}
