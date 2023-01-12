package com.quadint.app.domain.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @Controller 에서 반환할 최종 응답값
 */
@Getter
public class TimeRoute implements Comparable<TimeRoute>{

    /*** JSON 값 반환할 때 값을 설정한 포맷 형식의 String 반환 */
    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime time;
    private Route route;

    private TimeRoute(LocalDateTime time, Route route) {
        this.time = time;
        this.route = route;
    }

    public static TimeRoute createTimeRoute(LocalDateTime time, Route route) {
        return new TimeRoute(time, route);
    }

    @Override
    public int compareTo(TimeRoute o) {
        return this.time.compareTo(o.time);
    }
}
