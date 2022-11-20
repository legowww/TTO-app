package com.quadint.app.domain.route;

import lombok.Getter;

@Getter
public class LocationCoordinate {
    private String SX;
    private String SY;
    private String EX;
    private String EY;

    public LocationCoordinate(String SX, String SY, String EX, String EY) {
        this.SX = SX;
        this.SY = SY;
        this.EX = EX;
        this.EY = EY;
    }
}
