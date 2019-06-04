package com.toy.springboottoy.init;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.service.AccountService;
import com.toy.springboottoy.common.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import javax.transaction.Transactional;

@Slf4j
@Component
public class DefaultUserInitializer implements ApplicationRunner {

    private final AppProperties appProperties;
    private final AccountService accountService;

    public DefaultUserInitializer(AppProperties appProperties,
                                  AccountService accountService) {
        this.appProperties = appProperties;
        this.accountService = accountService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.debug("Initializing user data...");
        setFixtureAccount("manager", appProperties.getUserId(), appProperties.getUserPassword(), RoleType.MANAGER);
    }

    private void setFixtureAccount(String name,
                                   String email,
                                   String password,
                                   RoleType roleType) {

        Account account = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .roleType(roleType)
                .emailVerified(true)
                .build();
        accountService.signUp(account);
        log.debug("Init insert user : " + account.toString());
    }
}
