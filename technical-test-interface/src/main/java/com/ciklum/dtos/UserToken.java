package com.ciklum.dtos;

public class UserToken {
    private String token;

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
