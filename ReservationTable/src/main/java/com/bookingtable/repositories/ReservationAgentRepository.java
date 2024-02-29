package com.bookingtable.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.ReservationAgent;

@Repository
public interface ReservationAgentRepository extends JpaRepository<ReservationAgent, UUID> {

}
