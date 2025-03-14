package com.ak.live.convertor;


import com.ak.live.entities.Theater;
import com.ak.live.request.TheaterRequest;

public class TheaterConvertor {

    public static Theater theaterDtoToTheater(TheaterRequest theaterRequest) {
        Theater theater = Theater.builder()
                .name(theaterRequest.getName())
                .address(theaterRequest.getAddress())
                .build();
        return theater;
    }
}
