package com.ciklum.utilities;

import com.ciklum.dtos.User;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

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
    public void generate() {
        final User user = podamFactory.manufacturePojo(User.class);
        final String generatedToken = tokenGenerator.generate(user);
    }
}