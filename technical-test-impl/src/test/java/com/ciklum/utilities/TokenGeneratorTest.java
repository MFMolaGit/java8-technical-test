package com.ciklum.utilities;

import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import com.ciklum.exceptions.TokenGenerationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;

public class TokenGeneratorTest {

    private TokenGenerator tokenGenerator;

    private PodamFactory podamFactory;

    @Before
    public void setup() {
         tokenGenerator = new TokenGenerator();
         podamFactory = new PodamFactoryImpl();
    }

    //The UserToken instance will always be returned with a random delay between 0 and 5000 milliseconds.
    @Test(timeout=5000)
    public void testGenerateToken() throws InterruptedException, TokenGenerationException {
        //Given
        final User user = podamFactory.manufacturePojo(User.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String formatDateTime = formatter.format(new Date());
        String expectedToken = new StringBuilder(user.getUserId()).append("_").append(formatDateTime).toString();

        //When
        final UserToken generatedUserToken = tokenGenerator.generate(user);

        //Then
        Assert.assertNotNull(generatedUserToken);
        Assert.assertThat(generatedUserToken.getToken(), is(expectedToken));
    }
}