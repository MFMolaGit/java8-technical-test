package com.ciklum.dtos;

import java.io.Serializable;

public class UserToken implements Serializable {
    private String token;

    //Neccesary to serialization
    public UserToken() {
    }

    private UserToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public static class UserTokenBuilder {
        private String token;

        public UserTokenBuilder(String token) {
            this.token = token;
        }

        public UserToken build() {
            return new UserToken(token);
        }
    }
}
