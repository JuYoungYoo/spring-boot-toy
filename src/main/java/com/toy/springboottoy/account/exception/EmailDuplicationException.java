package com.toy.springboottoy.account.exception;

import lombok.Getter;

@Getter
public class EmailDuplicationException extends RuntimeException {

    private String field;
    private String email;

    public EmailDuplicationException(String email) {
        this.field = "email";
        this.email = email;
    }
}