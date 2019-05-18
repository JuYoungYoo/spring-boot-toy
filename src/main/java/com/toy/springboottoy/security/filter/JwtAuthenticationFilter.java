package com.toy.springboottoy.security.filter;

import com.toy.springboottoy.security.CustomUserDetailsService;
import com.toy.springboottoy.security.JwtTokenProvider;
import com.toy.springboottoy.security.model.JwtAuthenticationResponse;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.toy.springboottoy.security.model.JwtAuthenticationResponse.TOKEN_TYPE;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorization = request.getHeader("Authorization");
            String token = getAuthFromRequest(authorization);

            if (jwtTokenProvider.validateToken(token)) {
                long userId = jwtTokenProvider.getUserIdFromToken(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);

    }

    public String getAuthFromRequest(String authorization) {
        if (!authorization.startsWith(TOKEN_TYPE)) {
            throw new IllegalArgumentException(TOKEN_TYPE + " authentication is supported only.");
        }
        return getJwtToken(authorization);
    }

    private String getJwtToken(String authorization) {
        return authorization.replace(TOKEN_TYPE, " ").trim();
    }
}
