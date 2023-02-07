package com.example.dto.request;


import com.example.dto.LocationCoordinate;

public class FavoriteLocationCoordinateRequest {
    private String name;
    private String sx;
    private String sy;
    private String ex;
    private String ey;

    public FavoriteLocationCoordinateRequest(String name, String sx, String sy, String ex, String ey) {
        this.name = name;
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
    }

    public static FavoriteLocationCoordinateRequest fromLocationCoordinate(String name, LocationCoordinate lc) {
        return new FavoriteLocationCoordinateRequest(name, lc.getSx(), lc.getSy(), lc.getEx(), lc.getEy());
    }
}
