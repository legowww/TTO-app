package com.quadint.app.domain.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class BusArrivalTest {

    @Test
    public void timeTest() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String format = now.format(formatter);

        System.out.println(now.format(formatter));
        LocalDateTime now2 = now.plusMinutes(35);
        System.out.println(now2.format(formatter));
    }


    @Test
    public void logicTest() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime now = LocalDateTime.now();

        String lastBstopId = "164000345";
//        TimeResponse ba = new TimeResponse(now, "165000012");



    }
}