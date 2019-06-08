package com.toy.springboottoy.account.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountAdapterTest {

    @Test
    public void convertToAccount() {
        Account account = account();
        AccountAdapter accountAdapter = new AccountAdapter(account);

        assertThat(accountAdapter.getAccount()).isEqualTo(account);
    }

    private Account account() {
        return Account.builder()
                .name("user")
                .email("user@gmail.com")
                .password("pass")
                .roleType(RoleType.MANAGER)
                .state(true)
                .emailVerified(true)
                .build();
    }
}