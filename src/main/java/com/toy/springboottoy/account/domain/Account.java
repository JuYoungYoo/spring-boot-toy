package com.toy.springboottoy.account.domain;

import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.common.BaseAuditEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseAuditEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
                   String email,
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

    public void changePassword(AccountUpdateRequest.ChangePassword updateRequest) {
        password = updateRequest.getPassword();
    }

    public void disabled() {
        state = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + roleType.name()));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state;
    }

}