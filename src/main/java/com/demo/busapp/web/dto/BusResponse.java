package com.demo.busapp.web.dto;

import lombok.Getter;

/**
 * 모든 필드가 private 속성이면 JSON 직렬화 과정에서 No converter found for return value of type 에러
 * -> BusDto 의 각 필드 또는 메서드에 대해 @getter 추가하여 오류 해결
 * 하나라도 json 타입으로 변환된다면 에러는 발생하지 않는다.
 */
public class BusResponse {
    @Getter // @Getter 추가하지 않으면 JSON 응답화면에 출력되지 않는다.
    private String code;
    @Getter
    private BusDto busDto;
    public BusResponse(String code, BusDto busDto) {
        this.code = code;
        this.busDto = busDto;
    }
}
