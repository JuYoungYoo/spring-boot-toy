package com.toy.springboottoy.account.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class AccountAdapter extends User {

    private Account account;

    public AccountAdapter(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getRoleType()));
        this.account = account;
    }

    public static Collection<? extends GrantedAuthority> authorities(RoleType roleType) {
        return new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + roleType.name())));
    }

    public Account getAccount() {
        return account;
    }
}
