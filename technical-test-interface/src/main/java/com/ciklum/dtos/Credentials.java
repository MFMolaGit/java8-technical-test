package com.ciklum.dtos;

public class Credentials {

    private String username;
    private String password;

    private Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

    public static class CredentialsBuilder {

        private String username;
        private String password;

        public CredentialsBuilder withUsername(String username){
            this.username = username;
            return this;
        }

        public CredentialsBuilder withPassword(String password){
            this.password = password;
            return this;
        }

        public Credentials build() {
            return new Credentials(username, password);
        }
    }
}