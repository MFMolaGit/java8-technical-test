package com.ciklum.utilities;


import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import com.ciklum.exceptions.TokenGenerationException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TokenGenerator {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TOKEN_SEPARATOR = "_";

    public UserToken generate(User user) throws InterruptedException, TokenGenerationException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        String formatDateTime = formatter.format(new Date());
        final String userId = user.getUserId();

        if(userId.startsWith("A",0)) {
            throw new TokenGenerationException("Token generation failed");
        }

        String generatedToken = new StringBuilder(userId)
                .append(TOKEN_SEPARATOR).append(formatDateTime).toString();

        Thread.sleep(RandomGenerator.getRandomNumberInRange(0, 5000));
        return new UserToken.UserTokenBuilder(generatedToken).build();
    }
}
