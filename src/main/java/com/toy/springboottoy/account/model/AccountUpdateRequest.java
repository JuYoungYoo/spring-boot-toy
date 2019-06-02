package com.toy.springboottoy.account.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class AccountUpdateRequest {

    @Getter
    @NoArgsConstructor
    public static class ChangePassword {
        private String password;

        @Builder
        public ChangePassword(String password) {
            this.password = password;
        }

        public void encode(PasswordEncoder passwordEncoder) {
            password = passwordEncoder.encode(password);
        }
    }
}
