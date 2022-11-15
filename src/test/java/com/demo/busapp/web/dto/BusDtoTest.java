package com.demo.busapp.web.dto;

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
        BusDto busDto = new BusDto(temp);

        List<Bus> busDtos = busDto.getBusDto();
        for (Bus dto : busDtos) {
            System.out.println("dto = " + dto.toString());
        }
    }
}