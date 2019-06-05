package com.toy.springboottoy.account.domain;

import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.common.BaseAuditEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseAuditEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;
    private String imgProfileUrl;
    private boolean emailVerified;
    private boolean state;

    @Builder
    public Account(String name,
                   String password,
                   @Email String email,
                   RoleType roleType,
                   String imgProfileUrl,
                   boolean emailVerified,
                   boolean state) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleType = roleType;
        this.imgProfileUrl = imgProfileUrl;
        this.emailVerified = emailVerified;
        this.state = state;
    }

    public void changePassword(AccountUpdateRequest.ChangePassword changePassword) {
        this.password = changePassword.getPassword();
    }

    public void disabled() {
        state = false;
    }
}