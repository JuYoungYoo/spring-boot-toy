package com.toy.springboottoy.account.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    @Builder
    public SignInRequest(@NotEmpty String email,
                         @NotEmpty String password) {
        this.email = email;
        this.password = password;
    }
}
