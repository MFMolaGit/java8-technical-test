package com.ciklum.services;

import com.ciklum.dtos.*;

import javax.naming.AuthenticationException;

public interface ISyncTokenService {

    User authenticate(Credentials credentials) throws AuthenticationException, InterruptedException;

    UserToken requestToken(User user) throws InterruptedException;

    default UserToken issueToken(Credentials credentials) throws AuthenticationException, InterruptedException {
        final User authenticatedUser = authenticate(credentials);
        return requestToken(authenticatedUser);
    }

}
