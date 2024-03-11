package com.apiauth.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenNotRefreshException extends RuntimeException {
    public TokenNotRefreshException(String message) {
        super(message);
    }
}
