package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.dto.AccountDto;
import com.toy.springboottoy.account.service.UserService;
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

@RequestMapping(value = "/api/account/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService,
                          ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid AccountDto.SignUpReq accountDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Account newAccount = userService.signUp(accountDto);
        URI uri = linkTo(UserController.class).slash(newAccount.getId()).toUri();
        return ResponseEntity.created(uri).body(newAccount);
    }
}
