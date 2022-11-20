package com.quadint.app.domain.transportation;

import lombok.Getter;

@Getter
public abstract class Transportation {
    private TrafficType trafficType;
    private int time;

    public Transportation(TrafficType trafficType, int time) {
        this.trafficType = trafficType;
        this.time = time;
    }
}
