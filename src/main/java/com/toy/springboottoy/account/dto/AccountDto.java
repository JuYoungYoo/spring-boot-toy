package com.toy.springboottoy.account.dto;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {
        @NotEmpty
        private String userName;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;
        @Enumerated(value = EnumType.STRING)
        private Role role = Role.USER;
        private boolean mailYn;

        @Builder
        public SignUpReq(@NotEmpty String userName,
                         @NotEmpty String password,
                         @NotEmpty String email,
                         Role role,
                         boolean mailYn) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.role = role;
            this.mailYn = mailYn;
        }

        public Account toEntity() {
            return Account.builder()
                    .userName(this.userName)
                    .email(this.email)
                    .password(this.password)
                    .role(this.role)
                    .mailYn(this.mailYn)
                    .state(true)
                    .build();
        }
    }

    @Getter
    public static class Res {
        private String userName;
        private String email;
        private Role role;
        private boolean mailYn;
        private boolean state;

        @Builder
        public Res(String userName,
                   String email,
                   Role role,
                   boolean mailYn,
                   boolean state) {
            this.userName = userName;
            this.email = email;
            this.role = role;
            this.mailYn = mailYn;
            this.state = state;
        }
    }
}
