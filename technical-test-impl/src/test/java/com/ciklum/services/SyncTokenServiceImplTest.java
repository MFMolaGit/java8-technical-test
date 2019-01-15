package com.ciklum.services;

import com.ciklum.dtos.Credentials;
import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SyncTokenServiceImplTest {

    @InjectMocks
    private SyncTokenServiceImpl syncTokenService;

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
    public void testAuthenticateValidCredentials() throws AuthenticationException, InterruptedException {

        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);
        final User expectedUser = podamFactory.manufacturePojo(User.class);

        //When
        when(credentialsValidator.validate(any(Credentials.class))).thenReturn(expectedUser);

        final User user = syncTokenService.authenticate(credentials);

        //Then
        Assert.assertThat("User not expected", expectedUser, is(user));
    }

    @Test(expected = AuthenticationException.class)
    public void testAuthenticateNotValidCredentials() throws AuthenticationException, InterruptedException {
        //Given
        final Credentials credentials = podamFactory.manufacturePojo(Credentials.class);

        when(credentialsValidator.validate(any(Credentials.class))).thenThrow(AuthenticationException.class);

        //When
        final UserToken userToken = syncTokenService.issueToken(credentials);

        //Then
        //    Authenticaction Exception is launched and no requestToken method interaction
        verify(tokenGenerator, times(0)).generate(any(User.class));
    }

    @Test
    public void testRequestToken() throws InterruptedException {

        //Given
        final User user = podamFactory.manufacturePojo(User.class);
        final UserToken expectedUserToken = podamFactory.manufacturePojo(UserToken.class);

        //When
        when(tokenGenerator.generate(any(User.class))).thenReturn(expectedUserToken);

        final UserToken userToken = syncTokenService.requestToken(user);

        //Then
        Assert.assertThat("User not expected", userToken, is(expectedUserToken));
    }

}