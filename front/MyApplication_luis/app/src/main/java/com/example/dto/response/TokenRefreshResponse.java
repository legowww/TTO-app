package com.example.dto.response;

public class TokenRefreshResponse {
    private String access;

    public TokenRefreshResponse(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }
}
