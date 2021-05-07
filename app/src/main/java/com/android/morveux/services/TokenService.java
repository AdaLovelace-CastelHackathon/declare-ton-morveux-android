package com.android.morveux.services;

public class TokenService {

    private String token;

    private static TokenService INSTANCE;

    private TokenService() {}

    public static TokenService getInstance() {
        if(TokenService.INSTANCE == null) {
            TokenService.INSTANCE = new TokenService();
        }
        return TokenService.INSTANCE;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void removeToken() {
        this.token = null;
    }
}
