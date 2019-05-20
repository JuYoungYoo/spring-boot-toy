package com.toy.springboottoy.account.model;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountDto {
    @Getter
    @NoArgsConstructor
    public static class Res {
        private String userName;
        private String email;
        private RoleType roleType;
        private boolean mailYn;
        private boolean state;

        @Builder
        public Res(String userName,
                   String email,
                   RoleType roleType,
                   boolean mailYn,
                   boolean state) {
            this.userName = userName;
            this.email = email;
            this.roleType = roleType;
            this.mailYn = mailYn;
            this.state = state;
        }

        public static Res of(Account account){
            return new Res(account.getName(), account.getEmail(), account.getRoleType(), account.isEmailVerified(), account.isState());
        }
    }
}
