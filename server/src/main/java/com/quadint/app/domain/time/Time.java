package com.quadint.app.domain.time;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SUBWAY 클래스 추가 후
 * 상속 관계로 리팩토링 예정
 *              Time
 *      BusTime  SubwayTime
 */
@Getter
public class Time {
    private LocalDateTime time; //외출 시간

    private Time(LocalDateTime outTime) {
        this.time = outTime;
    }

    public static Time createTime(LocalDateTime time) {
        return new Time(time);
    }

    public static LocalDateTime calculateTime(LocalDateTime time, int arrivalTimeSec) {
        return time.plusSeconds(arrivalTimeSec);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return "Time{" +
                "time=" + time.format(formatter) +
                '}';
    }
}
