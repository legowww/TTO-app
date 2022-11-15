package com.demo.busapp.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Bus 일급 컬렉션 객체
 */
public class BusDto {
    // @Getter 없으므로 JSON 출력화면에서 제외
    private final List<Bus> busDtos;

    public BusDto(List<Bus> dtos) {
        this.busDtos = new ArrayList<>(dtos);
    }

    // 메서드는 JSON 출력 시 결과값 출력 대상
    public int getSize() {
        return busDtos.size();
    }

    //public 이므로 @Getter 없어도 JSON 출력
    public List<Bus> getBusDto() {
        return List.copyOf(busDtos);
    }
}
