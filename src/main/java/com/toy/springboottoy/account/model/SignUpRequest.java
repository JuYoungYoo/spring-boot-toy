package com.toy.springboottoy.account.model;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.AuthProvider;
import com.toy.springboottoy.account.domain.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;
    private boolean emailVerified = true;

    @Builder
    public SignUpRequest(@NotEmpty String name,
                     @NotEmpty String password,
                     @NotEmpty String email,
                     Role role,
                     boolean emailVerified) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.emailVerified = emailVerified;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
       password = passwordEncoder.encode(password);
    }

    public Account toEntity() {
        return Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .emailVerified(emailVerified)
                .state(true)
                .provider(new HashSet<>(Arrays.asList(AuthProvider.local)))
                .build();
    }
}
