package com.ak.live.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.live.entities.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Theater findByAddress(String address);
}
