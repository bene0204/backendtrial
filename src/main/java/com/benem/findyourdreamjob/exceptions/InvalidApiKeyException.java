package com.benem.findyourdreamjob.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidApiKeyException extends RuntimeException{
    public InvalidApiKeyException(String message) {
        super(message);
    }
}
