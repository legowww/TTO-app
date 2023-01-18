package com.quadint.app.web.controller;


import com.quadint.app.web.controller.request.LocationCoordinateRequest;
import com.quadint.app.domain.route.TimeRoute;
import com.quadint.app.web.controller.response.Response;
import com.quadint.app.web.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private final RouteService routeService;

    @PostMapping("/route")
    @ResponseBody
    public List<TimeRoute> getRoutes(@RequestBody LocationCoordinateRequest lc) {
        List<TimeRoute> result = routeService.calculateRoute(lc);
        return result;
    }

    //스프링 시큐리티 인가 테스트 URL
    @GetMapping("/test")
    @ResponseBody
    public Response<List<TimeRoute>> getRoutesDefault() {
        LocationCoordinateRequest lc = new LocationCoordinateRequest("126.6486573", "37.3908814", "126.6915832", "37.4384786"); // 퍼월->문학경기장
        List<TimeRoute> result = routeService.calculateRoute(lc);
        return Response.success(result);
    }
}

