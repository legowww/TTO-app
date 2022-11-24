package com.quadint.app.web.controller.test;


import com.quadint.app.domain.test.Bus;
import com.quadint.app.web.service.test.BusApiService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/retrofit")
@RequiredArgsConstructor
@Slf4j
public class RetrofitTestController {
    private final BusApiService busApiService;

    /**
     * TEST: @GET
     * 안드로이드 스튜디오에서 서버의 /retrofit/bus 으로 @GET REQUEST 를 요청을 보내면 서버는 RESPONSE 로 리스트 형태의 JSON 데이터 반환
     */
    @GetMapping("/bus")
    @ResponseBody
    public List<Bus> retrofitTest() {
        return busApiService.stationInformation("164000345");
    }

    /**
     * TEST: @POST
     * 안드로이드 스튜디오에서 서버의 /retrofit/json 으로 @POST REQUEST 를 보내면 서버는 RESPONSE 로 단일 JSON 데이터 반환
     */
    @PostMapping("/json")
    @ResponseBody
    public JsonData jsonTest(@RequestBody JsonData JsonData) {
        log.info("/json connected" + JsonData.name + " " + JsonData.age);
        return new JsonData("응답" + JsonData.name, 100 + JsonData.age);
    }

    @Getter
    @NoArgsConstructor
    static class JsonData {
        private String name;
        private int age;

        public JsonData(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
