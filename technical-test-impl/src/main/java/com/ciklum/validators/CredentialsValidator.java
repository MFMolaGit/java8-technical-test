package com.ciklum.validators;


import com.ciklum.dtos.Credentials;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidator {
    public boolean validate(Credentials credentials) {
        return false;
    }
}
