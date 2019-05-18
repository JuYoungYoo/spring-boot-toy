package com.toy.springboottoy.security.filter;

import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.security.JwtTokenProvider;
import com.toy.springboottoy.security.model.JwtAuthenticationResponse;
import com.toy.springboottoy.security.model.UserPrincipal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import static org.junit.Assert.*;

public class JwtAuthenticationFilterTest {


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtAuthenticationFilter filter;

    @Test
    @TestDescription("jwt token을 추출한다")
    public void obtain_jwt_token() {

    }

    @Test @TestDescription("authorization type을 확인한다")
    public void check_token_type() {
        String subject = String.valueOf(1);
        String token = jwtTokenProvider.generateToken(new HashMap<>(), subject);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(token);




    }
}