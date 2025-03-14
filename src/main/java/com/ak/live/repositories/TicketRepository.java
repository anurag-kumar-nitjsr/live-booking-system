package com.ak.live.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.live.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
}
