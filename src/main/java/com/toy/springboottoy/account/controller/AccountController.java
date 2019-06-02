package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/accounts", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        return accountService.signUp(signUpRequest);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account getAccountInfo(@PathVariable final long id) {
        return accountService.findById(id);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable final long id,
                               @RequestBody @Valid AccountUpdateRequest.ChangePassword changePwdRequest) {
        accountService.changePassword(id, changePwdRequest);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable final long id) {
        accountService.deleteAccount(id);
    }

    @GetMapping(value = "/me")
    public Map<String, Object> me(Authentication auth) {
        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
        Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
        return details;
    }
}
