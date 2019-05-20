package com.toy.springboottoy.account.model;

import com.toy.springboottoy.account.domain.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class SessionDto {

    @Getter
    @NoArgsConstructor
    public static class SignInReq {
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;

        @Builder
        public SignInReq(@NotEmpty String email,
                         @NotEmpty String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    public static class SignInRes {
        @NotEmpty
        private long id;
        @NotEmpty
        private String email;
        @NotEmpty
        private RoleType roleType;
        @NotEmpty
        private boolean state;

        @Builder
        public SignInRes(@NotEmpty long id,
                         @NotEmpty String email,
                         @NotEmpty RoleType roleType,
                         @NotEmpty boolean state) {
            this.id = id;
            this.email = email;
            this.roleType = roleType;
            this.state = state;
        }
    }

}
