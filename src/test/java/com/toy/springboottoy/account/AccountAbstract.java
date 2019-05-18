package com.toy.springboottoy.account;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.AuthProvider;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.model.AccountDto;
import com.toy.springboottoy.account.model.SignUpRequest;

import java.util.Arrays;
import java.util.HashSet;

public class AccountAbstract {

    public static Account getUser() {
        return Account.builder()
                .name("juyoung")
                .email("user@gmail.com")
                .password("password")
                .role(Role.USER)
                .state(true)
                .emailVerified(true)
                .provider(getAuthProviders(AuthProvider.local))
                .build();
    }

    private static HashSet<AuthProvider> getAuthProviders(AuthProvider... providers) {
        return new HashSet<>(Arrays.asList(providers));
    }

    public static SignUpRequest accountReqOf(String userName,
                                             String email) {
        String password = "password";
        Role role = Role.USER;
        return accountReqOf(userName, password, email, true, role);
    }

    public static SignUpRequest accountReqOf(String userName,
                                                    String email,
                                                    Role role) {
        String password = "password";
        return accountReqOf(userName, password, email, true, role);
    }

    public static SignUpRequest accountReqOf(String userName,
                                                    String password,
                                                    String email,
                                                    Boolean mailYn,
                                                    Role role) {
        return SignUpRequest.builder()
                .name(userName)
                .password(password)
                .email(email)
                .emailVerified(mailYn)
                .role(role)
                .build();
    }

    public static Account accountOf(String userName,
                                    String password,
                                    String email) {
        return setAccount(userName, password, email, Role.USER);
    }

    public static Account accountOf(String userName,
                                    String email,
                                    Role role) {
        return setAccount(userName, "password", email, role);
    }

    private static Account setAccount(String userName,
                                      String password,
                                      String email,
                                      Role role) {
        return Account.builder()
                .name(userName)
                .password(password)
                .email(email)
                .role(role)
                .emailVerified(true)
                .state(false)
                .build();
    }
}
