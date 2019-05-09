package com.toy.springboottoy.account.controller;

import com.toy.springboottoy.account.dto.SessionDto;
import com.toy.springboottoy.account.service.SessionService;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/account/session", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RestController
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SessionDto.signInRes signIn(@RequestBody SessionDto.signInReq signInReq) {
        return sessionService.signIn(signInReq);
    }

}
