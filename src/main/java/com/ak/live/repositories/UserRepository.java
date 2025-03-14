package com.ak.live.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ak.live.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailId(String emailId);;
}
