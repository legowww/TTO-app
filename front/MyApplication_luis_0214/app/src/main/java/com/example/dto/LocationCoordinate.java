package com.example.dto;


public class LocationCoordinate {
    private String sx;
    private String sy;
    private String ex;
    private String ey;

    public LocationCoordinate(String sx, String sy, String ex, String ey) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
    }

    public String getSx() {
        return sx;
    }

    public String getSy() {
        return sy;
    }

    public String getEx() {
        return ex;
    }

    public String getEy() {
        return ey;
    }

    @Override
    public String toString() {
        return "LocationCoordinate{" +
                "sx='" + sx + '\'' +
                ", sy='" + sy + '\'' +
                ", ex='" + ex + '\'' +
                ", ey='" + ey + '\'' +
                '}';
    }
}
