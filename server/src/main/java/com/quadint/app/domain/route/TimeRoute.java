package com.quadint.app.domain.route;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeRoute {
    private LocalDateTime time;
    private Route route;

    private TimeRoute(LocalDateTime time, Route route) {
        this.time = time;
        this.route = route;
    }

    public static TimeRoute createTimeRoute(LocalDateTime time, Route route) {
        return new TimeRoute(time, route);
    }
}
