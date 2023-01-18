package com.quadint.app.domain.time;

import com.quadint.app.domain.Time;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubwayTimeResponse {
    @Getter
    private String stationId;
    @Getter
    private String wayCode;
    @Getter @Setter
    private List<LocalDateTime> times = new ArrayList<>();

    private SubwayTimeResponse(String stationId, String wayCode) { this.stationId = stationId; this.wayCode = wayCode; }

    public static SubwayTimeResponse createSubwayTimeResponse(String stationId, String wayCode) {return new SubwayTimeResponse(stationId, wayCode);}

    @Override
    public String toString(){
        return "SubwayTimeResponse{" +
                "stationId='" + stationId + '\'' +
                "wayCode='" + wayCode + '\'' +
                ", list=" + times +
                '}';
    }
}
