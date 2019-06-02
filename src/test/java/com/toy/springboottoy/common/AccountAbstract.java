package com.toy.springboottoy.common;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SignUpRequest;

import java.util.Arrays;
import java.util.HashSet;

public class AccountAbstract {

    public static Account existUser() {
        return Account.builder()
                .name("manager")
                .email("manager@gmail.com")
                .password("pass")
                .roleType(RoleType.MANAGER)
                .state(true)
                .emailVerified(true)
                .build();
    }

    public static SignUpRequest accountReqOf(String userName,
                                             String email) {
        String password = "pass";
        RoleType roleType = RoleType.MANAGER;
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
                                    String email) {
        return setAccount(userName, "pass", email);
    }

    public static Account accountOf(String userName,
                                    String password,
                                    String email) {
        return setAccount(userName, "pass", email);
    }

    private static Account setAccount(String userName,
                                      String password,
                                      String email) {
        return Account.builder()
                .name(userName)
                .password(password)
                .email(email)
                .roleType(RoleType.MANAGER)
                .emailVerified(true)
                .state(false)
                .build();
    }
}
