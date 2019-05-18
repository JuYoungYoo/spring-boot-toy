package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.model.AccountDto;
import com.toy.springboottoy.account.service.AccountService;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountDto.Res getUser(@PathVariable final long id) {
        return accountService.findById(id);
    }

}
