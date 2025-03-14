package com.ak.live.convertor;

import com.ak.live.entities.Show;
import com.ak.live.request.ShowRequest;

public class ShowConvertor {

    public static Show showDtoToShow(ShowRequest showRequest) {
        return Show.builder()
                .time(showRequest.getShowStartTime()) // Ensure entity has `showTime`
                .date(showRequest.getShowDate())      // Ensure entity has `showDate`
                .build();
    }
}

