package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.UserToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.CompletableFuture;

public interface ISimpleAsyncTokenService {

    default CompletableFuture<UserToken> issueToken(Credentials credentials) {
        throw new NotImplementedException(); //Implemented on impl module
    }

}
