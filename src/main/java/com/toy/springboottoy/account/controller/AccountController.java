package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.dto.AccountDto;
import com.toy.springboottoy.account.service.AccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RequestMapping(value = "/api/account", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RestController
public class AccountController {


    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AccountController(AccountService accountService,
                             ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto accountDto, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        Account account =  modelMapper.map(accountDto, Account.class);
        Account newAccount = accountService.signUp(account);
        URI uri = linkTo(AccountController.class).slash(newAccount.getId()).toUri();
        return ResponseEntity.created(uri).body(account);
    }
}
