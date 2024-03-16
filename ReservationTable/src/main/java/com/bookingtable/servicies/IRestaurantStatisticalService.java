package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.models.RestaurantStatistical;
import com.bookingtable.models.RevenueStatistics;

public interface IRestaurantStatisticalService {

	List<RestaurantStatistical> findAll(String emailAgent,String restaurantId);
	boolean create(RestaurantStatistical restaurantStatistical);
	
}
