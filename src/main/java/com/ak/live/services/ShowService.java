package com.ak.live.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.live.convertor.ShowConvertor;
import com.ak.live.entities.Live;
import com.ak.live.entities.Show;
import com.ak.live.entities.ShowSeat;
import com.ak.live.entities.Theater;
import com.ak.live.entities.TheaterSeat;
import com.ak.live.enums.SeatType;
import com.ak.live.exceptions.LiveDoesNotExists;
import com.ak.live.exceptions.ShowDoesNotExists;
import com.ak.live.exceptions.TheaterDoesNotExists;
import com.ak.live.repositories.LiveRepository;
import com.ak.live.repositories.ShowRepository;
import com.ak.live.repositories.TheaterRepository;
import com.ak.live.request.ShowRequest;
import com.ak.live.request.ShowSeatRequest;

@Service
public class ShowService {

    @Autowired
    private LiveRepository liveRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    public String addShow(ShowRequest showRequest) {
        Show show = ShowConvertor.showDtoToShow(showRequest);

        Optional<Live> liveOpt = liveRepository.findById(showRequest.getLiveId());
        if (liveOpt.isEmpty()) {
            throw new LiveDoesNotExists();
        }

        Optional<Theater> theaterOpt = theaterRepository.findById(showRequest.getTheaterId());
        if (theaterOpt.isEmpty()) {
            throw new TheaterDoesNotExists();
        }

        Theater theater = theaterOpt.get();
        Live live = liveOpt.get();

        show.setLive(live);
        show.setTheater(theater);
        
        show = showRepository.save(show);

        live.getShows().add(show);
        theater.getShowList().add(show);

        liveRepository.save(live);
        theaterRepository.save(theater);

        return "Show has been added successfully";
    }

    public String associateShowSeats(ShowSeatRequest showSeatRequest) throws ShowDoesNotExists {
        Optional<Show> showOpt = showRepository.findById(showSeatRequest.getShowId());
        if (showOpt.isEmpty()) {
            throw new ShowDoesNotExists();
        }

        Show show = showOpt.get();
        Theater theater = show.getTheater();

        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for (TheaterSeat theaterSeat : theaterSeatList) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setSeatNo(theaterSeat.getSeatNo());
            showSeat.setSeatType(theaterSeat.getSeatType());

            if (showSeat.getSeatType().equals(SeatType.CLASSIC)) {
                showSeat.setPrice(showSeatRequest.getPriceOfClassicSeat());
            } else {
                showSeat.setPrice(showSeatRequest.getPriceOfPremiumSeat());
            }

            showSeat.setShow(show);
            showSeat.setIsAvailable(Boolean.TRUE);
            showSeat.setIsFoodContains(Boolean.FALSE);

            showSeatList.add(showSeat);
        }

        showRepository.save(show);

        return "Show seats have been associated successfully";
    }
}