package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import com.ciklum.utilities.TokenGenerator;
import com.ciklum.validators.CredentialsValidator;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class SyncTokenServiceImpl implements ISyncTokenService {

    private CredentialsValidator credentialsValidator;
    private TokenGenerator tokenGenerator;

    public SyncTokenServiceImpl(CredentialsValidator credentialsValidator, TokenGenerator tokenGenerator) {
        this.credentialsValidator = credentialsValidator;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public User authenticate(Credentials credentials) throws AuthenticationException {
        if(!credentialsValidator.validate(credentials)) {
            throw new AuthenticationException();
        }
        return new User.UserBuilder(credentials.getUsername()).build();
    }

    @Override
    public UserToken requestToken(User user) {
        return new UserToken.UserTokenBuilder(tokenGenerator.generate(user)).build();
    }
}
