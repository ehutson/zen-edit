package com.acoustic.zenedit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserInfoException extends RuntimeException {
    public DuplicateUserInfoException(final String message) {
        super(message);
    }
}
