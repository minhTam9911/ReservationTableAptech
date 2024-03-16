package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.RestaurantStatistical;

@Repository
public interface RestaurantStatisticalRepository extends JpaRepository<RestaurantStatistical, String> {

	List<RestaurantStatistical> findByRestaurant(String restaurant);
	
}
