package com.ciklum.utilities;


import com.ciklum.dtos.User;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TokenGenerator {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TOKEN_SEPARATOR = "_";

    public String generate(User user) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        String formatDateTime = formatter.format(new Date());
        String generatedToken = new StringBuilder(user.getUserId())
                .append(TOKEN_SEPARATOR).append(formatDateTime).toString();
        return generatedToken;
    }
}
