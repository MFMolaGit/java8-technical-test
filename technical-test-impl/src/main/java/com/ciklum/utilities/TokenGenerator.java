package com.ciklum.utilities;


import com.ciklum.dtos.User;
import com.ciklum.dtos.UserToken;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TokenGenerator {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TOKEN_SEPARATOR = "_";

    public UserToken generate(User user) throws InterruptedException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        String formatDateTime = formatter.format(new Date());
        String generatedToken = new StringBuilder(user.getUserId())
                .append(TOKEN_SEPARATOR).append(formatDateTime).toString();

        Thread.sleep(RandomGenerator.getRandomNumberInRange(0, 5000));
        return new UserToken.UserTokenBuilder(generatedToken).build();
    }
}
