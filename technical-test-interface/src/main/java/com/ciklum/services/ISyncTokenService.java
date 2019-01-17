package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;

public interface ISyncTokenService {

    User authenticate(Credentials credentials) throws Throwable;

    UserToken requestToken(User user) throws Throwable;

    default UserToken issueToken(Credentials credentials) throws Throwable {
        final User authenticatedUser = authenticate(credentials);
        return requestToken(authenticatedUser);
    }

}
