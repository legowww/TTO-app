package com.quadint.app.web.controller;


import com.quadint.app.domain.route.LocationCoordinate;
import com.quadint.app.domain.route.Route;
import com.quadint.app.domain.route.TimeRoute;
import com.quadint.app.domain.time.BusTimeResponse;
import com.quadint.app.domain.time.Time;
import com.quadint.app.web.service.time.BusArrivalApiService;
import com.quadint.app.web.service.route.RouteApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusApiController {
    private final BusArrivalApiService busArrivalApiService;
    private final RouteApiService routeApiService;

    @GetMapping("/route")
    @ResponseBody
    public List<TimeRoute> getRoutes() {
//        LocationCoordinate lc = new LocationCoordinate("126.6698164", "37.40518939", "126.63652", "37.37499041");
        LocationCoordinate lc = new LocationCoordinate("126.64417910828391", "37.388578171104086", "126.63652", "37.37499041"); //송도컨벤시아
        List<Route> routes = routeApiService.getRoutes(lc);


        List<TimeRoute> timeRoutes = new ArrayList<>();
        for (Route route : routes) {
            List<String> first = route.getFirstTransportation();
            String bstopId = first.get(0); //처음만나는 정류장ID
            String routeId = first.get(1); //처음만는 정류장에서 타는 버스ID
            int walkTime = Integer.parseInt(first.get(2)); //처음만나는 정류장까지의 걷는시간

            BusTimeResponse r = busArrivalApiService.getTimeResponse(bstopId, routeId);
            for (int i = 0; i < r.getTimeSize(); i++) {
                Time time = r.getTime(i);
                LocalDateTime busArrivalTime = time.getTime(); //버스 도착시간
                LocalDateTime timeToGo = busArrivalTime.minusMinutes(walkTime + 3); //버스 도착시간 - 정류장까지 걷는시간 - 여유시간(3)

                TimeRoute timeRoute = TimeRoute.createTimeRoute(timeToGo, route);
                timeRoutes.add(timeRoute);
            }
        }
        return timeRoutes;
    }

    @GetMapping("/time")
    @ResponseBody
    public BusTimeResponse getBusArrivalStationTimes() {
        BusTimeResponse timeResponse = busArrivalApiService.getTimeResponse("164000345", "165000012");
        return timeResponse;
    }
}
