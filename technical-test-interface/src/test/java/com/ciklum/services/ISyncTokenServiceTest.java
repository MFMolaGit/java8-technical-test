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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ISyncTokenServiceTest {

    @Mock
    private ISyncTokenService iSyncTokenService;

    private PodamFactory podamFactory;

    @Before
    public void setup() {
        podamFactory = new PodamFactoryImpl();
    }


    @Test
    public void testIssueTokenValidCredentials() throws Throwable {
        //Given
        final User user = podamFactory.manufacturePojo(User.class);
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);
        final UserToken expectedUserToken = podamFactory.manufacturePojo(UserToken.class);

        when(iSyncTokenService.issueToken(any(Credentials.class))).thenCallRealMethod();
        when(iSyncTokenService.authenticate(any(Credentials.class))).thenReturn(user);
        when(iSyncTokenService.requestToken(any(User.class))).thenReturn(expectedUserToken);


        //When
        final UserToken userToken = iSyncTokenService.issueToken(credentials);

        //Then
        //    return validated user
        Assert.assertThat(userToken, is(expectedUserToken));
    }

    @Test(expected = AuthenticationException.class)
    public void testIssueTokenNotValidCredentials() throws Throwable {
        //Given
        final User user = podamFactory.manufacturePojo(User.class);
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);
        final UserToken expectedUserToken = podamFactory.manufacturePojo(UserToken.class);

        when(iSyncTokenService.issueToken(any(Credentials.class))).thenCallRealMethod();
        when(iSyncTokenService.authenticate(any(Credentials.class))).thenThrow(AuthenticationException.class);

        //When
        final UserToken userToken = iSyncTokenService.issueToken(credentials);

        //Then
        //    Authenticaction Exception is launched and no requestToken method interaction
        verify(iSyncTokenService, times(0)).requestToken(any(User.class));
    }

}