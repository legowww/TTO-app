package com.quadint.app.domain.transportation;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Walk extends Transportation{
    public Walk(TrafficType trafficType, int time) {
        super(trafficType, time);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String toString() {
        return "도보로 " + super.getTime() + "분 이동";
    }
}
