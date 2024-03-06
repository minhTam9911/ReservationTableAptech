package com.bookingtable.repositories;


import com.bookingtable.models.Restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
	List<Restaurant> findByReservationAgentEmail(String email);
}
