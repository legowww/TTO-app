package com.quadint.app.domain.transportation;

import lombok.Getter;

@Getter
public class Bus extends Transportation{
    private String routeId;
    private String busNum;
    private String startLocalStationID;
    private String startName;
    private String endLocalStationID;
    private String endName;

    public Bus(TrafficType trafficType, int time, String routeId, String busNum, String startLocalStationID, String startName, String endLocalStationID, String endName) {
        super(trafficType, time);
        this.routeId = routeId;
        this.busNum = busNum;
        this.startLocalStationID = startLocalStationID;
        this.startName = startName;
        this.endLocalStationID = endLocalStationID;
        this.endName = endName;
    }

    @Override
    public String toString() {
        return busNum + "(" + routeId + ")" + "번 버스로 " + super.getTime() + "분 소요하여 " + startName + "(" + startLocalStationID + ")정류장 에서 " + endName + "(" + endLocalStationID + ")정류장 까지 이동";
    }
}
