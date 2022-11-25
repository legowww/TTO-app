package com.quadint.app.domain.transportation;

public class Subway extends Transportation{
    public Subway(TrafficType trafficType, int time) {
        super(trafficType, time);
    }

    @Override
    public String getId() {
        return null;
    }
}
