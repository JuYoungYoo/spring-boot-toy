package com.toy.springboottoy.account;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.AuthProvider;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SignUpRequest;

import java.util.Arrays;
import java.util.HashSet;

public class AccountAbstract {

    public static Account getUser() {
        return Account.builder()
                .name("juyoung")
                .email("user@gmail.com")
                .password("password")
                .roleType(RoleType.USER)
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
        RoleType roleType = RoleType.USER;
        return accountReqOf(userName, password, email, true, roleType);
    }

    public static SignUpRequest accountReqOf(String userName,
                                                    String email,
                                                    RoleType roleType) {
        String password = "password";
        return accountReqOf(userName, password, email, true, roleType);
    }

    public static SignUpRequest accountReqOf(String userName,
                                                    String password,
                                                    String email,
                                                    Boolean mailYn,
                                                    RoleType roleType) {
        return SignUpRequest.builder()
                .name(userName)
                .password(password)
                .email(email)
                .emailVerified(mailYn)
                .roleType(roleType)
                .build();
    }

    public static Account accountOf(String userName,
                                    String password,
                                    String email) {
        return setAccount(userName, password, email, RoleType.USER);
    }

    public static Account accountOf(String userName,
                                    String email,
                                    RoleType roleType) {
        return setAccount(userName, "password", email, roleType);
    }

    private static Account setAccount(String userName,
                                      String password,
                                      String email,
                                      RoleType roleType) {
        return Account.builder()
                .name(userName)
                .password(password)
                .email(email)
                .roleType(roleType)
                .emailVerified(true)
                .state(false)
                .build();
    }
}
