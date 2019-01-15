package com.ciklum.services;

import com.ciklum.dtos.*;

import javax.naming.AuthenticationException;

public interface ISyncTokenService {

    User authenticate(Credentials credentials) throws AuthenticationException;

    UserToken requestToken(User user);

    default UserToken issueToken(Credentials credentials) throws AuthenticationException {
        final User authenticatedUser = authenticate(credentials);
        return requestToken(authenticatedUser);
    }

}
