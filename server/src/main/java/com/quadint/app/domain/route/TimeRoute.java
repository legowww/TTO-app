package com.quadint.app.domain.route;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeRoute implements Comparable<TimeRoute>{
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
