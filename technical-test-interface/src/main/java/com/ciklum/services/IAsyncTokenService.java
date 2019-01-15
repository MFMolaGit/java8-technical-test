package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface IAsyncTokenService {

    CompletableFuture<User> authenticate(Credentials credentials);

    CompletableFuture<UserToken> requestToken(User user);

    default Future<UserToken> issueToken(Credentials credentials) {
        throw new NotImplementedException(); //TODO: Implement this
    }

}
