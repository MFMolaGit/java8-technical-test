package com.ciklum.validators;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import org.junit.Assert;
import org.junit.Test;

/*
 * If the password matches the username in uppercase, the validation is a success, otherwise is a failure. Examples:
 */
public class CredentialsValidatorTest {

    private CredentialsValidator credentialsValidator = new CredentialsValidator();

    // username: house , password: HOUSE => Valid credentials.
    @Test
    public void testValidateCredentials() {
        //Given
        Credentials credentials = new Credentials.CredentialsBuilder().withUsername("house")
                .withPassword("HOUSE").build();
        //When
        final boolean validation = credentialsValidator.validate(credentials);

        //Then
        Assert.assertTrue(validation);
    }

    // username: house , password: House => Invalid credentials.
    @Test
    public void testInvalidateCredentials() {
        //Given
        Credentials credentials = new Credentials.CredentialsBuilder().withUsername("house")
                .withPassword("House").build();
        //When
        final boolean validation = credentialsValidator.validate(credentials);

        //Then
        Assert.assertFalse(validation);
    }

}