package com.toy.springboottoy.security.component;


import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.config.AppProperties;
import com.toy.springboottoy.security.CustomUserDetailsService;
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
        setFixtureAccount("admin", appProperties.getAdminId(), appProperties.getAdminPassword(), RoleType.MANAGER);
        setFixtureAccount("user", appProperties.getUserId(), appProperties.getUserPassword(), RoleType.USER);
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
            customUserDetailsService.signUp(account);
        }
    }
}
