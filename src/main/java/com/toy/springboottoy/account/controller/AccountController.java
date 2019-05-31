package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account getUser(@PathVariable final long id) {
        return accountService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        return accountService.signUp(signUpRequest);
    }


}
