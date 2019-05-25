package com.toy.springboottoy.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Setter
@Configuration
@ConfigurationProperties("my-app.auth")
public class AuthProperties {

    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
    @NotEmpty
    private JwtInfo jwtInfo = new JwtInfo();

    @Setter
    private static class JwtInfo {
        private Map<String,Object> jwtInfo;
        private String secret;
        private long expirationInMs;

        public String getSecret() {
            return jwtInfo.get("secret").toString();
        }

        public long getExpirationInMs() {
            return (long) jwtInfo.get("expirationInMs");
        }
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public JwtInfo getJwtInfo() {
        return jwtInfo;
    }
}