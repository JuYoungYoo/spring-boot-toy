package com.toy.springboottoy.error;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ErrorResponse {

    private String message;
    private String code;
    private int status;
    private List<FieldError> errors = new ArrayList<>();

    @Builder
    public ErrorResponse(String message, String code, int status, List<FieldError> errors) {
        this.message = message;
        this.code = code;
        this.status = status;
        if(errors == null){
            this.errors = new ArrayList<>();
        }else{
            this.errors = errors;
        }
//        this.errors.addAll(errors);
    }

    @Getter
    public static class FieldError{
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field,
                          String value,
                          String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}