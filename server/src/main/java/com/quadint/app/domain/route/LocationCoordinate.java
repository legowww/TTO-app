package com.quadint.app.domain.route;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "LocationCoordinate{" +
                "SX='" + sx + '\'' +
                ", SY='" + sy + '\'' +
                ", EX='" + ex + '\'' +
                ", EY='" + ey + '\'' +
                '}';
    }
}
