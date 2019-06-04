package com.toy.springboottoy.account.model;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;
    private boolean emailVerified;

    @Builder
    public SignUpRequest(@NotEmpty String name,
                     @NotEmpty String password,
                     @NotEmpty String email,
                     RoleType roleType,
                     boolean emailVerified) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleType = roleType;
        this.emailVerified = emailVerified;
    }
}
