package com.demo.busapp.web.controller;


import com.demo.busapp.web.dto.Buses;
import com.demo.busapp.web.dto.BusResponse;
import com.demo.busapp.web.service.BusApiService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusApiController {
    private final BusApiService busApiService;

    @GetMapping("/bstop")
    @ResponseBody
    public BusResponse bstopTest() {
        try {
            return new BusResponse("정상", new Buses(busApiService.stationInformation("164000345")));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @GetMapping("/route")
    @ResponseBody
    public String routeTest(){
        try {
            busApiService.busInformation("165000012");
            return "complete";
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @PostMapping("/json")
    @ResponseBody
    public Buses jsonTest(@RequestBody JsonData jsonData) {
        try {
            return new Buses(busApiService.stationInformation(jsonData.getBstopid()));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    @Getter
    @NoArgsConstructor
    static class JsonData {
        private String message;
        private String bstopid;
    }
}
