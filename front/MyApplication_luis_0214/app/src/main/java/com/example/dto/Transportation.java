package com.example.dto;

public class Transportation {
    //COMMON FIELD
    private int time;
    private String transportationType;

    //BUS ONLY FIELD
    private String busNum;
    private String startName;
    private String endName;

    public String getBusNum() {
        return busNum;
    }

    public String getStartName() {
        return startName;
    }

    public String getEndName() {
        return endName;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public int getTime() {
        return time;
    }
}
