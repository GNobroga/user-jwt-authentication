package com.apiauth.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenProcessingException extends RuntimeException {
    public TokenProcessingException(String message) {
        super(message);
    }
}
