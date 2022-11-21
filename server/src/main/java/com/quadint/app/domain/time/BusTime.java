package com.quadint.app.domain.time;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BusTime {
    private String lastBstopId;
    private int arrivalTimeSec;

    public BusTime(String lastBstopId, int arrivalTimeSec) {
        this.lastBstopId = lastBstopId;
        this.arrivalTimeSec = arrivalTimeSec;
    }
}
