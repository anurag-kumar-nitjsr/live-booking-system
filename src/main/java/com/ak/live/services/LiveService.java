package com.ak.live.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.live.convertor.LiveConvertor;
import com.ak.live.entities.Live;
import com.ak.live.exceptions.LiveAlreadyExist;
import com.ak.live.repositories.LiveRepository;
import com.ak.live.request.LiveRequest;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;

    public String addLive(LiveRequest liveRequest) {
        Live liveByName = liveRepository.findByMovieName(liveRequest.getLiveName());

        if (liveByName != null && liveByName.getLanguage().equals(liveRequest.getLanguage())) {
            throw new LiveAlreadyExist("Live event already exists with this name and language.");
        }

//        Live live = LiveConvertor.liveRequestToLive(liveRequest);

//        liveRepository.save(live);
        return "The live event has been added successfully";
    }
}
