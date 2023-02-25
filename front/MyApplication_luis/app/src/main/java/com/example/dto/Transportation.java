package com.example.dto;

import java.io.Serializable;

public class Transportation implements Serializable {
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

    @Override
    public String toString() {
        return "Transportation{" +
                "time=" + time +
                ", transportationType='" + transportationType + '\'' +
                ", busNum='" + busNum + '\'' +
                ", startName='" + startName + '\'' +
                ", endName='" + endName + '\'' +
                '}';
    }
}

