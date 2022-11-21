package com.quadint.app.domain.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Bus 일급 컬렉션 객체
 */
public class Buses {
    // @Getter 없으므로 JSON 출력화면에서 제외
    private final List<Bus> buses;

    public Buses(List<Bus> dtos) {
        this.buses = new ArrayList<>(dtos);
    }

    // 메서드는 JSON 출력 시 결과값 출력 대상
    public int getSize() {
        return buses.size();
    }

    //public 이므로 @Getter 없어도 JSON 출력
    public List<Bus> getBusDto() {
        return List.copyOf(buses);
    }
}
