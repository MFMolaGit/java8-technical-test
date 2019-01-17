package com.ciklum.controllers;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.UserToken;
import com.ciklum.services.ISimpleAsyncTokenService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.concurrent.CompletableFuture;

@RestController
public class TokenServiceController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TokenServiceController.class);


    private ISimpleAsyncTokenService tokenService;

    public TokenServiceController(ISimpleAsyncTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(path = "/tokens", consumes = "application/json", produces = "application/json")
    public CompletableFuture<ResponseEntity<UserToken>> generateToken(@RequestBody Credentials credentials) {
        return tokenService.issueToken(credentials)
                .thenApply(userToken -> ResponseEntity.ok(userToken))
                .exceptionally(e -> {
                    if(e.getCause() instanceof AuthenticationException) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }

}
