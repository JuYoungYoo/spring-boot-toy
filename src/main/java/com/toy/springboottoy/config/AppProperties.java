package com.toy.springboottoy.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Component
@ConfigurationProperties("my-app")
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private String adminId;
    @NotEmpty
    private String adminPassword;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private Auth auth = new Auth();

    @Setter @Getter
    @ToString
    private static class Auth {
        private Map<String, Object> jwt;
        private String secret;
        private long expirationInMs;

        public String getSecret() {
            return jwt.get("secret").toString();
        }

        public Long getExpirationInMs() {
            return Long.parseLong(jwt.get("expirationInMs").toString());
        }
    }
}
