package com.bookingtable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String>  {

}
