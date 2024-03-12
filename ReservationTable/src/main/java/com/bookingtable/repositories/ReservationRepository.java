package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String>  {
	List<Reservation> findByCustomer_Id(String customer_id);
}
