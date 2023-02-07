package com.example.dto;


public class TimeRoute {
    private String time;

    private Route route;

    public Route getRoute() {
        return route;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TimeRoute{" +
                "time='" + time + '\'' +
                ", route=" + route +
                '}';
    }
}
