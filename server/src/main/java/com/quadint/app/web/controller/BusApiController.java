package com.quadint.app.web.controller;


import com.quadint.app.domain.test.Bus;
import com.quadint.app.domain.test.Buses;
import com.quadint.app.domain.test.BusResponse;
import com.quadint.app.domain.route.LocationCoordinate;
import com.quadint.app.domain.route.Route;
import com.quadint.app.domain.route.Routes;
import com.quadint.app.domain.time.BusTimeResponse;
import com.quadint.app.web.service.test.BusApiService;
import com.quadint.app.web.service.time.BusArrivalApiService;
import com.quadint.app.web.service.route.RouteApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusApiController {
    private final BusArrivalApiService busArrivalApiService;
    private final RouteApiService routeApiService;

    @GetMapping("/route")
    @ResponseBody
    public List<Route> getRoutes() {
        LocationCoordinate lc = new LocationCoordinate("126.6698164", "37.40518939", "126.63652", "37.37499041");
        Routes routes = routeApiService.getRoutes(lc);
        return routes.getOptimalRoutes();
    }

    @GetMapping("/time")
    @ResponseBody
    public BusTimeResponse getBusArrivalStationTimes() {
        BusTimeResponse timeResponse = busArrivalApiService.getTimeResponse("164000345", "165000012");
        System.out.println("timeResponse = " + timeResponse);
        return timeResponse;
    }
}
