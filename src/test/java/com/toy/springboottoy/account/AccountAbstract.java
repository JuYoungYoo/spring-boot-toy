package com.toy.springboottoy.account;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.dto.AccountDto;

public class AccountAbstract {

    public static Account getUser() {
        return Account.builder()
                .userName("juyoung")
                .email("user@gmail.com")
                .password("password")
                .role(Role.USER)
                .state(true)
                .mailYn(true)
                .build();
    }

    public static AccountDto.SignUpReq accountReqOf(String userName, String email){
        String password = "password";
        Role role = Role.USER;
        return accountReqOf(userName, password, email, true, role);
    }

    public static AccountDto.SignUpReq accountReqOf(String userName, String email, Role role){
        String password = "password";
        return accountReqOf(userName, password, email, true, role);
    }

    public static AccountDto.SignUpReq accountReqOf(String userName,
                                                    String password,
                                                    String email,
                                                    Boolean mailYn,
                                                    Role role){
        return  AccountDto.SignUpReq.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .mailYn(mailYn)
                .role(role)
                .build();
    }

    public static Account accountOf(String userName, String password, String email){
        return setAccount(userName, password, email, Role.USER);
    }

    public static Account accountOf(String userName, String email, Role role){
        return setAccount(userName, "password", email, role);
    }

    private static Account setAccount(String userName,
                                      String password,
                                      String email,
                                      Role role) {
        return Account.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .role(role)
                .mailYn(true)
                .state(false)
                .build();
    }
}
