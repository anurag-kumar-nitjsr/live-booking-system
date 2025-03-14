package com.ak.live.convertor;

import com.ak.live.entities.Live;
import com.ak.live.request.LiveRequest;

public class LiveConvertor {

    public static Live convertToLiveEntity(LiveRequest liveRequest) {
        return Live.builder()
                .liveName(liveRequest.getLiveName())  // Fixed incorrect method call
                .duration(liveRequest.getDuration())
                .genre(liveRequest.getGenre())
                .language(liveRequest.getLanguage())
                .releaseDate(liveRequest.getReleaseDate())
                .rating(liveRequest.getRating())
                .build();
    }
}
