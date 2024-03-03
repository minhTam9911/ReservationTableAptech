package com.bookingtable.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.ReservationAgent;
import com.bookingtable.models.System;

@Repository
public interface ReservationAgentRepository extends JpaRepository<ReservationAgent, String> {
	ReservationAgent findByEmail(String email);
	@Query(value = "select * from reservation_agent where email = :email and id <> :id", nativeQuery = true)
	ReservationAgent existEmail(@Param("email")String email, @Param("id") String id);
}
