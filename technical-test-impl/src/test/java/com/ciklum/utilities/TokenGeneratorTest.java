package com.ciklum.utilities;

import com.ciklum.dtos.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TokenGeneratorTest {

    private TokenGenerator tokenGenerator;

    private PodamFactory podamFactory;

    @Before
    public void setup() {
         tokenGenerator = new TokenGenerator();
         podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testGenerateToken() {
        //Given
        final User user = podamFactory.manufacturePojo(User.class);
        final String generatedToken = tokenGenerator.generate(user);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String formatDateTime = formatter.format(new Date());

        //When
        String expectedToken = new StringBuilder(user.getUserId()).append("_").append(formatDateTime).toString();

        //Then
        Assert.assertThat(generatedToken, is(expectedToken));
    }
}