package com.apiauth.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
