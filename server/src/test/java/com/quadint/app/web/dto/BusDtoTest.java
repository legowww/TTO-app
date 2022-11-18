package com.quadint.app.web.dto;

import com.quadint.app.web.service.BusApiService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BusDtoTest {

    @Test
    public void test() throws Exception {
        Bus busA = new Bus("1", "2", "3");
        Bus busB = new Bus("4", "5", "6");
        Bus busC = new Bus("7", "7", "7");

        List<Bus> temp = new ArrayList<>();
        temp.add(busA);
        temp.add(busB);
        Buses busDto = new Buses(temp);

        List<Bus> busDtos = busDto.getBusDto();
        for (Bus dto : busDtos) {
            System.out.println("dto = " + dto.toString());
        }
    }

    /**
     * 출발 정류장: 164000345 에서 인접한 8번 버스 정보 2개를 가져온다.
     */
    @Test
    public void apiTest() throws Exception {
        //given
        BusApiService busApiService = new BusApiService();
        List<Bus> buses = busApiService.stationInformation("164000345");


        //when



        //then
    }


}