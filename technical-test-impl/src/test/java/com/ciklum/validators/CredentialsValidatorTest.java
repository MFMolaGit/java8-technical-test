package com.ciklum.validators;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.AuthenticationException;

/*
 * If the password matches the username in uppercase, the validation is a success, otherwise is a failure. Examples:
 */
public class CredentialsValidatorTest {

    private CredentialsValidator credentialsValidator = new CredentialsValidator();

    // username: house , password: HOUSE => Valid credentials.
    //The User instance will always be returned with a random delay between 0 and 5000 milliseconds.
    @Test(timeout=5000)
    public void testValidateCredentials() throws AuthenticationException, InterruptedException {
        //Given
        Credentials credentials = new Credentials.CredentialsBuilder().withUsername("house")
                .withPassword("HOUSE").build();
        //When
        final User validatedUser = credentialsValidator.validate(credentials);

        //Then
        Assert.assertNotNull("Valid credentials expected", validatedUser);
        Assert.assertEquals("UserId not expected", validatedUser.getUserId(), credentials.getUsername());
    }

    // username: house , password: House => Invalid credentials.
    @Test(expected = AuthenticationException.class)
    public void testInvalidateCredentials() throws AuthenticationException, InterruptedException {
        //Given
        Credentials credentials = new Credentials.CredentialsBuilder().withUsername("house")
                .withPassword("House").build();
        //When
        final User validatedUser = credentialsValidator.validate(credentials);

        //Then AuthenticationException
    }

}