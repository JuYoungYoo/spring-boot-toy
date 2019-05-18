package com.toy.springboottoy.security;

import com.toy.springboottoy.security.model.UserPrincipal;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String JWT_SECRET_KEY = "HS123";
    private static int jwtExpirationInMs = 604800000;


    public String generateToken(UserPrincipal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        String id = Long.toString(userPrincipal.getId());
        return generateToken(claims, id);
    }

    public String generateToken(Map<String,Object> claims, String subject){
        Date createDate = new Date();
        Date expirationDate = new Date(createDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(subject))
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
