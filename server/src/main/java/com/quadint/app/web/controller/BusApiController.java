package com.quadint.app.web.controller;


import com.quadint.app.web.dto.Bus;
import com.quadint.app.web.dto.Buses;
import com.quadint.app.web.dto.BusResponse;
import com.quadint.app.web.service.BusApiService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusApiController {
    private final BusApiService busApiService;

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

    @GetMapping("/json")
    @ResponseBody
    public List<Bus> jsonTest(@RequestBody JsonData jsonData) {
        return busApiService.stationInformation(jsonData.getBstopid());
    }

    @GetMapping("/test")
    @ResponseBody
    public List<Bus> retrofitTest() {
        return busApiService.stationInformation("164000345");
    }

    @Getter
    @NoArgsConstructor
    static class JsonData {
        private String message;
        private String bstopid;
    }
}
