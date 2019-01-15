package com.ciklum.dtos;

public class User {
    private String userId;

    private User(String userId) {
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public static class UserBuilder {
        private String userId;

        public UserBuilder(String userId) {
            this.userId = userId;
        }

        public User build() {
            return new User(userId);
        }
    }

}