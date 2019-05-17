package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.model.SessionDto;
import com.toy.springboottoy.account.model.SignInRequest;
import com.toy.springboottoy.security.JwtTokenProvider;
import com.toy.springboottoy.security.model.JwtAuthenticationResponse;
import com.toy.springboottoy.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/session")
@RequiredArgsConstructor
public class SessionController {

    private JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @GetMapping
    public String loginPage(@RequestBody SessionDto.SignInReq signInReq) {
        return "login";
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest signInReq) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getEmail(), signInReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(userPrincipal);
        return new JwtAuthenticationResponse(token);
    }

}
