package com.example.dto.response;

public class LoginSuccessTokenResponse {
    private String access;
    private String refresh;

    public LoginSuccessTokenResponse(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public String getRefresh() {
        return refresh;
    }
}
