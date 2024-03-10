package com.apiauth.handler;

import java.time.OffsetTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.apiauth.exceptions.TokenExpiredException;
import com.apiauth.exceptions.TokenNotRefreshException;
import com.apiauth.exceptions.TokenProcessingException;
import com.apiauth.exceptions.UserExistsException;
import com.apiauth.exceptions.UserNotFoundException;


@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException exception) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
            problemDetail.setTitle("Not found");
            problemDetail.setDetail("Unable to find user");
            problemDetail.setProperty("timestamp", OffsetTime.now());
            return problemDetail;
    }

    @ExceptionHandler(UserExistsException.class)
    public ProblemDetail handleUserExistsException(UserExistsException exception) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
            problemDetail.setTitle("Conflict");
            problemDetail.setDetail("User or email is already registered");
            problemDetail.setProperty("timestamp", OffsetTime.now());
            return problemDetail;
    }

    @ExceptionHandler(TokenProcessingException.class)
    public ProblemDetail handleTokenProcessingException(TokenProcessingException exception) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
            problemDetail.setTitle("Token");
            problemDetail.setDetail("Unable to generate a token");
            problemDetail.setProperty("timestamp", OffsetTime.now());
            return problemDetail;
    } 

    @ExceptionHandler(TokenExpiredException.class)
    public ProblemDetail handleTokenExpiredException(TokenExpiredException exception) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
            problemDetail.setTitle("Token");
            problemDetail.setDetail("The token is expired");
            problemDetail.setProperty("timestamp", OffsetTime.now());
            return problemDetail;
    }

    @ExceptionHandler(TokenNotRefreshException.class)
    public ProblemDetail handleTokenNotRefreshException(TokenNotRefreshException exception) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
            problemDetail.setTitle("Token");
            problemDetail.setDetail("The token is not a refresh token");
            problemDetail.setProperty("timestamp", OffsetTime.now());
            return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());
            problemDetail.setTitle("Intern Error");
            problemDetail.setDetail(exception.getMessage());
            problemDetail.setProperty("timestamp", OffsetTime.now());
            return problemDetail;
    }

}
