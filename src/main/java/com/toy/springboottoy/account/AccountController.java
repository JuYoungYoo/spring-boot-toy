package com.toy.springboottoy.account;

import com.toy.springboottoy.account.CurrentUser;
import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        Account account = modelMapper.map(signUpRequest, Account.class);
        return accountService.signUp(account);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account getAccount(@PathVariable final long id) {
        return accountService.findById(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@CurrentUser Account account,
                               @RequestBody @Valid AccountUpdateRequest.ChangePassword changePwdRequest) {
        accountService.changePassword(account.getId(), changePwdRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@CurrentUser Account account) {
        accountService.deleteAccount(account.getId());
    }
}