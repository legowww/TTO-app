package com.quadint.app.web.dto;

import com.quadint.app.domain.route.LocationCoordinate;
import com.quadint.app.domain.route.Route;
import com.quadint.app.domain.transportation.Transportation;
import com.quadint.app.web.service.route.RouteApiService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class BusDtoTest {
    /**
     * 1. 출발지, 목적지 좌표 입력
     * 2. 경로 계산
     * 3. 소요시간 적은 순으로 경로 3개 추출
     * 4.
     *
     *
     * 출발 정류장: 164000345 에서 인접한 8번 버스 정보 2개를 가져온다.
     */
    @Test
    public void apiTest() throws Exception {
        LocationCoordinate lc = new LocationCoordinate("126.6698164", "37.40518939", "126.63652", "37.37499041");
        RouteApiService routeApiService = new RouteApiService();
        List<Route> result = routeApiService.getRoutes(lc);
        for (int i = 0; i < result.size(); ++i) {
            Route route = result.get(i);
            System.out.println(i+1 + ". " + route);
            List<Transportation> transportationList = route.getTransportation();
            for (Transportation transportation : transportationList) {
                System.out.println("\t" + transportation.toString());
            }
            System.out.println();
        }
    }
}