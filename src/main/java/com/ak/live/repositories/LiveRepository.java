package com.ak.live.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.live.entities.Live;

public interface LiveRepository extends JpaRepository<Live, Integer> {
	Live findByMovieName(String name);
}