package com.demo.busapp.web.controller;


import com.demo.busapp.web.dto.BusDto;
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

    @GetMapping
    @ResponseBody
    public BusResponse bus() {
        try {
            BusDto busDto = new BusDto(busApiService.stationInformation("164000345"));
            return new BusResponse("정상", busDto);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * App -> Server
     * JSON 데이터 전송
     */
    @PostMapping("/json")
    @ResponseBody
    public BusDto bus2(@RequestBody JsonTest jsonTest) {
        try {
            BusDto busDto = new BusDto(busApiService.stationInformation(jsonTest.getBstopid()));
            return busDto;
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Getter
    @NoArgsConstructor
    static class JsonTest {
        private String message;
        private String bstopid;
    }
}
