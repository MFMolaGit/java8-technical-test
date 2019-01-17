package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class IAsyncTokenServiceTest {

    @Mock
    private IAsyncTokenService iAsyncTokenService;

    private PodamFactory podamFactory;

    @Before
    public void setup() {
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testIssueTokenValidCredentials() throws ExecutionException, InterruptedException {
        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);
        final User user = podamFactory.manufacturePojo(User.class);
        final UserToken userToken = podamFactory.manufacturePojo(UserToken.class);

        when(iAsyncTokenService.issueToken(any(Credentials.class))).thenCallRealMethod();
        when(iAsyncTokenService.authenticate(any(Credentials.class)))
                .thenReturn(CompletableFuture.completedFuture(user));
        when(iAsyncTokenService.requestToken(any(User.class)))
                .thenReturn(CompletableFuture.completedFuture(userToken));

        //When
        final Future<UserToken> tokenIssuing = iAsyncTokenService.issueToken(credentials);

        //Then
        Assert.assertFalse(tokenIssuing.isCancelled());
        Assert.assertTrue(tokenIssuing.isDone());
        Assert.assertEquals(tokenIssuing.get(), userToken);
    }

    @Test(expected = ExecutionException.class)
    public void testIssueTokenNotValidCredentials() throws InterruptedException, ExecutionException {
        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);

        CompletableFuture<User> failAuthentication = new CompletableFuture<>();
        failAuthentication.completeExceptionally(new AuthenticationException("Authentication failed"));

        when(iAsyncTokenService.issueToken(any(Credentials.class))).thenCallRealMethod();
        when(iAsyncTokenService.authenticate(any(Credentials.class)))
                .thenReturn(failAuthentication);

        //When
        final Future<UserToken> tokenIssuing = iAsyncTokenService.issueToken(credentials);

        //Then
        Assert.assertFalse(tokenIssuing.isCancelled());
        Assert.assertTrue(tokenIssuing.isDone());
        tokenIssuing.get();
    }
}