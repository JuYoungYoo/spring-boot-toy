package com.toy.springboottoy.security;

import com.toy.springboottoy.account.domain.Account;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        Account userPrincipal = (Account) authentication.getPrincipal();
//        authentication.setDetails(userPrincipal);
        additionalInfo.put("user_id", userPrincipal.getId());
        additionalInfo.put("email", userPrincipal.getEmail());
        additionalInfo.put("organization", "toy");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}