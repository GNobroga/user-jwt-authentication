package com.apiauth;

import java.time.Instant;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apiauth.exceptions.TokenNotRefreshException;
import com.apiauth.interfaces.IAuthenticationService;
import com.apiauth.models.LoginDTO;
import com.apiauth.models.RefreshTokenDTO;
import com.apiauth.utils.TokenResponseHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "auth", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    private final IAuthenticationService authenticationService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    @ResponseStatus(code = HttpStatus.OK)
    public TokenResponseHandler post(@RequestBody @Valid LoginDTO login) {
        logger.info("Trying to authenticate the user [POST]");
        var token = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var authentication = authenticationManager.authenticate(token);
        return  TokenResponseHandler.builder()
            .accessToken(authenticationService.generateToken(authentication, Instant.now(), false))
            .refreshToken(authenticationService.generateRefreshToken(authentication))
            .tokenType("Bearer")
            .build();
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(code = HttpStatus.OK)
    public TokenResponseHandler post(@RequestBody @Valid RefreshTokenDTO token) {
        logger.info("Trying to renew user token [POST]");
        if (authenticationService.isRefreshToken(token.getRefreshToken())) {
            return  TokenResponseHandler.builder()
                .accessToken(authenticationService.refreshToken(token.getRefreshToken(), false))
                .refreshToken(authenticationService.refreshToken(token.getRefreshToken(), true))
                .tokenType("Bearer")
                .build();
        }
        throw new TokenNotRefreshException();
    }
}
