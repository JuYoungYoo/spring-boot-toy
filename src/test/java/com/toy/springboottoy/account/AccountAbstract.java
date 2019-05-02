package com.toy.springboottoy.account;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;

public class AccountAbstract {


    public static Account accountOf(String username, String email){
        String password = "password";
        Role role = Role.USER;
        return createAccount(username, password, email, true, true, role);
    }

    public static Account accountOf(String username, String email, Role role){
        String password = "password";
        return createAccount(username, password, email, true, true, role);
    }

    public static Account createAccount(String username, String password, String email, Boolean mailYn, Boolean state, Role role){
        Account account = Account.builder()
                .userName(username)
                .password(password)
                .email(email)
                .mailYn(mailYn)
                .state(state)
                .role(role)
                .build();
        return account;
    }

}
