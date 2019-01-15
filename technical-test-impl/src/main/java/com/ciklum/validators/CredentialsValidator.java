package com.ciklum.validators;


import com.ciklum.dtos.Credentials;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidator {
    public boolean validate(Credentials credentials) {
        final String upperUser = credentials.getUsername().toUpperCase();
        final String upperPass = credentials.getPassword();
        return upperUser.equals(upperPass);
    }
}
