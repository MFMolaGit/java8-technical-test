package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import com.ciklum.exceptions.TokenGenerationException;
import com.ciklum.utilities.TokenGenerator;
import com.ciklum.validators.CredentialsValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.naming.AuthenticationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAsyncTokenServiceImplTest {

    @InjectMocks
    private SimpleAsyncTokenServiceImpl simpleAsyncTokenService;

    @Mock
    private CredentialsValidator credentialsValidator;

    @Mock
    private TokenGenerator tokenGenerator;

    private PodamFactory podamFactory;

    @Before
    public void setup() {
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testIssueTokenCompleted() throws AuthenticationException, InterruptedException, ExecutionException {
        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);
        final User user = podamFactory.manufacturePojo(User.class);
        final UserToken userToken = podamFactory.manufacturePojo(UserToken.class);

        when(credentialsValidator.validate(any(Credentials.class)))
                .thenReturn(user);
        when(tokenGenerator.generate(any(User.class)))
                .thenReturn(userToken);

        //When
        final Future<UserToken> tokenIssuing = simpleAsyncTokenService.issueToken(credentials);

        //Then
        Assert.assertEquals(tokenIssuing.get(), userToken);
    }

    @Test(expected = ExecutionException.class)
    public void testIssueTokenNotValidCredentials() throws Throwable {
        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);

        when(credentialsValidator.validate(any(Credentials.class)))
                .thenThrow(AuthenticationException.class);

        //When
        final CompletableFuture<UserToken> tokenIssuing = simpleAsyncTokenService.issueToken(credentials);

        //Then
        Assert.assertFalse(tokenIssuing.isDone());
        try {
            tokenIssuing.get();
        } catch (ExecutionException e) {
            Assert.assertTrue(e.getCause() instanceof AuthenticationException);
            throw e;
        }
    }

    @Test(expected = ExecutionException.class)
    public void testIssueTokenGenerationFailed() throws Throwable {
        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);
        final User user = podamFactory.manufacturePojo(User.class);

        when(credentialsValidator.validate(any(Credentials.class)))
                .thenReturn(user);
        when(tokenGenerator.generate(any(User.class)))
                .thenThrow(TokenGenerationException.class);

        //When
        final CompletableFuture<UserToken> tokenIssuing = simpleAsyncTokenService.issueToken(credentials);

        //Then
        try {
            tokenIssuing.get();
        } catch (ExecutionException e) {
            Assert.assertTrue(e.getCause() instanceof TokenGenerationException);
            throw e;
        }
    }
}