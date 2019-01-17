package com.ciklum.exceptions;

import java.util.concurrent.ExecutionException;

public class TokenGenerationException extends ExecutionException {

    public TokenGenerationException(String message) {
        super(message);
    }

}
