package com.quadint.app.web.controller.test;



import com.quadint.app.domain.test.BusResponse;
import com.quadint.app.domain.test.Buses;
import com.quadint.app.web.service.test.BusApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestApiController {
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
}
