package com.example.dto.response;

public class MyPageResponse {
    private String name;
    private String username;

    public MyPageResponse(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}