package com.businessrules.matrix.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDecisionRequestException extends RuntimeException {

    public InvalidDecisionRequestException(String message) {
        super(message);
    }
}
