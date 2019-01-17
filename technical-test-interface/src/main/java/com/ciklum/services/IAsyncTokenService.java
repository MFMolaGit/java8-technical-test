package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface IAsyncTokenService {

    CompletableFuture<User> authenticate(Credentials credentials);

    CompletableFuture<UserToken> requestToken(User user);

    default Future<UserToken> issueToken(Credentials credentials) {
        return authenticate(credentials)
                .thenCompose(user -> requestToken(user))
                .exceptionally(e -> {throw new RuntimeException(e);});
    }

}
