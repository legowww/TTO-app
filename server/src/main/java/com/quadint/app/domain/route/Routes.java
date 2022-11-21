package com.quadint.app.domain.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Routes {
    private final List<Route> routes;

    public Routes(List<Route> routes) {
        this.routes = new ArrayList<>(routes);
    }

    public List<Route> getOptimalRoutes() {
        Collections.sort(this.routes);
        return List.copyOf(routes);
        //todo: 2~3개 루트를 추출하는 로직 추가 예정
//        return List.copyOf(List.of(routes.get(0), routes.get(1), routes.get(2)));
    }
}
