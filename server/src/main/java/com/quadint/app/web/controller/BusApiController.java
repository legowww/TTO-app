package com.quadint.app.web.controller;


import com.quadint.app.domain.bus.Buses;
import com.quadint.app.domain.bus.BusResponse;
import com.quadint.app.domain.route.LocationCoordinate;
import com.quadint.app.domain.route.Route;
import com.quadint.app.domain.route.Routes;
import com.quadint.app.web.service.bus.BusApiService;
import com.quadint.app.web.service.route.RouteApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusApiController {
    private final BusApiService busApiService;
    private final RouteApiService routeApiService;


    @GetMapping("/bstop")
    @ResponseBody
    public BusResponse bstopTest() {
        return new BusResponse("정상", new Buses(busApiService.stationInformation("164000345")));
    }

    @GetMapping("/route")
    @ResponseBody
    public String routeTest() {
        busApiService.busInformation("165000012");
        return "complete";
    }

    @GetMapping("routes")
    @ResponseBody
    public List<Route> getRoutes() {
        LocationCoordinate lc = new LocationCoordinate("126.6698164", "37.40518939", "126.63652", "37.37499041");
        Routes routes = routeApiService.getRoutes(lc);
        return routes.getOptimalRoutes();
    }

}
