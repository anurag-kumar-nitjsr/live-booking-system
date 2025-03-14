package com.ak.live.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.live.convertor.TicketConvertor;
import com.ak.live.entities.Show;
import com.ak.live.entities.ShowSeat;
import com.ak.live.entities.Ticket;
import com.ak.live.entities.User;
import com.ak.live.exceptions.SeatsNotAvailable;
import com.ak.live.exceptions.ShowDoesNotExists;
import com.ak.live.exceptions.UserDoesNotExists;
import com.ak.live.repositories.ShowRepository;
import com.ak.live.repositories.TicketRepository;
import com.ak.live.repositories.UserRepository;
import com.ak.live.request.TicketRequest;
import com.ak.live.response.TicketResponse;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private UserRepository userRepository;

	public TicketResponse ticketBooking(TicketRequest ticketRequest) {
		Optional<Show> showOpt = showRepository.findById(ticketRequest.getShowId());

		if (showOpt.isEmpty()) {
			throw new ShowDoesNotExists();
		}

		Optional<User> userOpt = userRepository.findById(ticketRequest.getUserId());

		if (userOpt.isEmpty()) {
			throw new UserDoesNotExists();
		}

		User user = userOpt.get();
		Show show = showOpt.get();

		Boolean isSeatAvailable = isSeatAvailable(show.getShowSeatList(), ticketRequest.getRequestSeats());

		if (!isSeatAvailable) {
			throw new SeatsNotAvailable();
		}

		// count price
		Integer getPriceAndAssignSeats = getPriceAndAssignSeats(show.getShowSeatList(),	ticketRequest.getRequestSeats());

		String seats = listToString(ticketRequest.getRequestSeats());

		Ticket ticket = new Ticket();
		ticket.setTotalTicketsPrice(getPriceAndAssignSeats);
		ticket.setBookedSeats(seats);
		ticket.setUser(user);
		ticket.setShow(show);

		ticket = ticketRepository.save(ticket);

		user.getTicketList().add(ticket);
		show.getTicketList().add(ticket);
		userRepository.save(user);
		showRepository.save(show);

		return TicketConvertor.returnTicket(show, ticket);
	}

	private Boolean isSeatAvailable(List<ShowSeat> showSeatList, List<String> requestSeats) {
		for (ShowSeat showSeat : showSeatList) {
			String seatNo = showSeat.getSeatNo();

			if (requestSeats.contains(seatNo) && !showSeat.getIsAvailable()) {
				return false;
			}
		}

		return true;
	}

	private Integer getPriceAndAssignSeats(List<ShowSeat> showSeatList, List<String> requestSeats) {
		Integer totalAmount = 0;

		for (ShowSeat showSeat : showSeatList) {
			if (requestSeats.contains(showSeat.getSeatNo())) {
				totalAmount += showSeat.getPrice();
				showSeat.setIsAvailable(Boolean.FALSE);
			}
		}

		return totalAmount;
	}

	private String listToString(List<String> requestSeats) {
		StringBuilder sb = new StringBuilder();

		for (String s : requestSeats) {
			sb.append(s).append(",");
		}

		return sb.toString();
	}

}
