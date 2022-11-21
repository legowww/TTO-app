package com.quadint.app.domain.test;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Bus {
    private String routeid;
    private String bstopId;
    private int arrivalTimeSec;

    public Bus(String routeid, String bstopId, int arrivalTimeSec) {
        this.routeid = routeid;
        this.bstopId = bstopId;
        this.arrivalTimeSec = arrivalTimeSec;
    }

}
