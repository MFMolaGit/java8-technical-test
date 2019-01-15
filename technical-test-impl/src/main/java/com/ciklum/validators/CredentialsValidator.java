package com.ciklum.validators;


import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.utilities.RandomGenerator;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public class CredentialsValidator {

    public User validate(Credentials credentials) throws AuthenticationException, InterruptedException {
        final String upperUser = credentials.getUsername().toUpperCase();
        final String upperPass = credentials.getPassword();
        boolean valid = upperUser.equals(upperPass);

        if(!valid) {
            throw new AuthenticationException();
        }
        Thread.sleep(RandomGenerator.getRandomNumberInRange(0, 5000));
        return new User.UserBuilder(credentials.getUsername()).build();
    }

}
