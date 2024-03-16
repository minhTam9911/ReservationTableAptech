package com.bookingtable.repositories;

import com.bookingtable.models.Rate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
	List<Rate> findByCustomerEmail(String email);
	List<Rate> findByReservationId(String id);

}
