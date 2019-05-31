package com.toy.springboottoy.security.model;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.security.UserPrincipal;
import org.junit.Test;

import static com.toy.springboottoy.common.AccountAbstract.existUser;
import static org.assertj.core.api.Assertions.assertThat;

public class UserPrincipalTest {

    @Test @TestDescription("계정 정보로 인증 객체 생성")
    public void create_from_account() {
        Account account = existUser();
        UserPrincipal result = UserPrincipal.of(account);
        assertThat(result.getEmail()).isEqualTo(account.getEmail());
        assertThat(result.getUsername()).isEqualTo(account.getName());
    }
}