package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import com.ciklum.exceptions.TokenGenerationException;
import com.ciklum.utilities.TokenGenerator;
import com.ciklum.validators.CredentialsValidator;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class SimpleAsyncTokenServiceImpl implements ISimpleAsyncTokenService {

    private CredentialsValidator credentialsValidator;
    private TokenGenerator tokenGenerator;

    public SimpleAsyncTokenServiceImpl(CredentialsValidator credentialsValidator, TokenGenerator tokenGenerator) {
        this.credentialsValidator = credentialsValidator;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public CompletableFuture<UserToken> issueToken(Credentials credentials) {
        CompletableFuture<User> authentication = CompletableFuture.supplyAsync(() -> {
            try {
                return credentialsValidator.validate(credentials);
            } catch (InterruptedException|AuthenticationException e) {
                throw new CompletionException(e);
            }
        });

        return authentication.thenCompose(user -> CompletableFuture.supplyAsync(() -> {
            try {
                return tokenGenerator.generate(user);
            } catch (InterruptedException|TokenGenerationException e) {
                throw new CompletionException(e);
            }
        }));

    }
}
