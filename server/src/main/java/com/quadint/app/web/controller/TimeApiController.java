package com.quadint.app.web.controller;


import com.quadint.app.domain.route.LocationCoordinate;
import com.quadint.app.domain.route.Route;
import com.quadint.app.domain.route.TimeRoute;
import com.quadint.app.domain.time.BusTimeResponse;
import com.quadint.app.domain.time.Time;
import com.quadint.app.web.service.BusArrivalApiService;
import com.quadint.app.web.service.RouteApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/route")
@RequiredArgsConstructor
public class TimeApiController {
    private final BusArrivalApiService busArrivalApiService;
    private final RouteApiService routeApiService;

    @PostMapping
    @ResponseBody
    public List<TimeRoute> getRoutes(@RequestBody LocationCoordinate lc) {
        System.out.println("lc = " + lc.toString());

        //최적 경로3개
        List<Route> tempRoutes = routeApiService.getRoutes(lc);
        List<Route> routes = List.copyOf(List.of(tempRoutes.get(0), tempRoutes.get(1), tempRoutes.get(2)));

//        List<Route> routes = routeApiService.getRoutes(lc);
        //todo: 일급 컬렉션 TimeRoutes 추가
        List<TimeRoute> timeRoutes = new ArrayList<>();
        for (Route route : routes) {
            //todo: 버스 전용 로직이므로 지하철 추가 시 수정
            List<String> first = route.getFirstTransportation();
            String firstBstopId = first.get(0); //처음만나는 정류장ID
            String firstRouteId = first.get(1); //처음만는 정류장에서 타는 버스ID
            int walkTimeMin = Integer.parseInt(first.get(2)); //처음만나는 정류장까지의 걷는시간(분)

            BusTimeResponse r = busArrivalApiService.getTimeResponse(firstBstopId, firstRouteId);
            for (int i = 0; i < r.getTimeSize(); i++) {
                Time time = r.getTime(i);
                LocalDateTime busArrivalTime = time.getTime(); //버스 도착시간
                LocalDateTime timeToGo = busArrivalTime.minusMinutes(walkTimeMin + 2); //나갈시간 = 버스 도착시간 - 정류장까지 걷는시간 + 보정시간(2)
                TimeRoute timeRoute = TimeRoute.createTimeRoute(timeToGo, route);
                timeRoutes.add(timeRoute);
            }
        }
        Collections.sort(timeRoutes); //나갈시간 빠른 순서대로 정렬

        List<TimeRoute> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(); //현재 시간
        for (int i = 0; i < timeRoutes.size(); ++i) {
            TimeRoute tr = timeRoutes.get(i);
            if (tr.getTime().compareTo(now) != -1) { //현재 시간보다 이후인 시간대만 result 추가
                result.add(tr);
            }
        }
        return result;
    }

    @GetMapping("/test")
    @ResponseBody
    public List<TimeRoute> getRoutesDefault() {
//        LocationCoordinate lc = new LocationCoordinate("126.6709157", "37.4056074", "126.63652", "37.37499041");
        LocationCoordinate lc = new LocationCoordinate("126.6486573", "37.3908814", "126.63652", "37.37499041"); //송도 더샵 퍼스트월드 단지

        //최적 경로3개
        List<Route> tempRoutes = routeApiService.getRoutes(lc);
        List<Route> routes = List.of(tempRoutes.get(0), tempRoutes.get(1), tempRoutes.get(2));

        //todo: 일급 컬렉션 TimeRoutes 추가
        List<TimeRoute> timeRoutes = new ArrayList<>();
        for (Route route : routes) {
            //todo: 버스 전용 로직이므로 지하철 추가 시 수정
            List<String> first = route.getFirstTransportation();
            String firstBstopId = first.get(0); //처음만나는 정류장ID
            String firstRouteId = first.get(1); //처음만는 정류장에서 타는 버스ID
            int walkTimeMin = Integer.parseInt(first.get(2)); //처음만나는 정류장까지의 걷는시간(분)

            BusTimeResponse r = busArrivalApiService.getTimeResponse(firstBstopId, firstRouteId); //실시간 버스 도착 시간대 3개 GET
            for (int i = 0; i < r.getTimeSize(); i++) {
                Time time = r.getTime(i);
                LocalDateTime busArrivalTime = time.getTime(); //버스 도착시간
                LocalDateTime timeToGo = busArrivalTime.minusMinutes(walkTimeMin + 2); //나갈시간 = 버스 도착시간 - 정류장까지 걷는시간 + 보정시간(2)
                TimeRoute timeRoute = TimeRoute.createTimeRoute(timeToGo, route);
                timeRoutes.add(timeRoute);
            }
        }
        Collections.sort(timeRoutes); //나갈시간이 빠른 순서대로 정렬

        List<TimeRoute> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(); //현재 시간
        for (int i = 0; i < timeRoutes.size(); ++i) {
            TimeRoute tr = timeRoutes.get(i);
            if (tr.getTime().compareTo(now) != -1) { //현재 시간보다 이후인 시간대만 result 추가
                result.add(tr);
            }
        }
        return result;
    }
}
