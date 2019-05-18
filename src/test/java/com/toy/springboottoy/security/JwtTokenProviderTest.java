package com.toy.springboottoy.security;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.security.model.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static com.toy.springboottoy.account.AccountAbstract.getUser;
import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenProviderTest {

    @InjectMocks
    JwtTokenProvider jwtTokenProvider;

    private static final String JWT_SECRET_KEY = "HS123";
    private static int jwtExpirationInMs = 604800000;

    @Before
    public void name() {
        MockitoAnnotations.initMocks(this);
    }

    @Test @TestDescription("JWT 토큰을 발급한다")
    public void generateToken() {
        Account account = getUser();
        UserPrincipal userPrincipal = UserPrincipal.of(account);
        String token = jwtTokenProvider.generateToken(userPrincipal);
        assertThat(token).isNotNull();
    }

    @Test @TestDescription("토큰 검증")
    public void validateToken() {
        Account account = getUser();
        UserPrincipal userPrincipal = UserPrincipal.of(account);
        String token = jwtTokenProvider.generateToken(userPrincipal);

        boolean result = jwtTokenProvider.validateToken(token);

        assertThat(result).isTrue();
    }
}