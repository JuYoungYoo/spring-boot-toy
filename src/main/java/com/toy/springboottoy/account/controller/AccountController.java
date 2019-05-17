package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.AccountDto;
import com.toy.springboottoy.account.service.AccountService;
import org.modelmapper.internal.Errors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody @Valid AccountDto.SignUpReq accountDto,
                          Errors errors) {
        Account account = accountService.signUp(accountDto);
        return account;
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountDto.Res getUser(@PathVariable final long id) {
        return accountService.findById(id);
    }

}
