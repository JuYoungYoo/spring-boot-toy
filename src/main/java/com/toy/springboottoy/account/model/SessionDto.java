package com.toy.springboottoy.account.model;

import com.toy.springboottoy.account.domain.Role;
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
        private Role role;
        @NotEmpty
        private boolean state;

        @Builder
        public SignInRes(@NotEmpty long id,
                         @NotEmpty String email,
                         @NotEmpty Role role,
                         @NotEmpty boolean state) {
            this.id = id;
            this.email = email;
            this.role = role;
            this.state = state;
        }
    }

}
