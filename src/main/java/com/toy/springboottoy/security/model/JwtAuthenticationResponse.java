package com.toy.springboottoy.security.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class JwtAuthenticationResponse {

    public static final String TOKEN_TYPE = "Bearer";
    private String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
