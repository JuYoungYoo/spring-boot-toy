package com.toy.springboottoy.security;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Collection;
import java.util.Map;

// todo :: OAuth2AuthenticationDetails info
@Data
@NoArgsConstructor
public class UserInfo {

    private Long id;
    private String name;
    private String email;
    private String organization;
    private Collection<? extends GrantedAuthority> authorities;
    private long exp;
    private String jti;
    private Map<String, String> details;

    public UserInfo(Authentication authentication) {
        UserInfo userInfo = new UserInfo();
        OAuth2AuthenticationDetails oAuth2Details = (OAuth2AuthenticationDetails) authentication.getDetails();
        details = (Map<String, String>) oAuth2Details.getDecodedDetails();
        bind();
    }

    private void bind() {
        id = Long.parseLong(details.get("user_id"));
        name = details.get("user_name");
        email = details.get("email");
        organization = details.get("organization");
//        authorities = details.get("auth")
        id = Long.parseLong(details.get("user_id"));

    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
    }*/
}
