package com.toy.springboottoy.account.dto;

import com.toy.springboottoy.account.domain.Role;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;
    private boolean mailYn;
}
