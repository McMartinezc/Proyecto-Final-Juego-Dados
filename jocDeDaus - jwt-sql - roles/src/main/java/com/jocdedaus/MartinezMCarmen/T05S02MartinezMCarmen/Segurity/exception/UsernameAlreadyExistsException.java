package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyExistsException extends ResponseStatusException {

    public UsernameAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}