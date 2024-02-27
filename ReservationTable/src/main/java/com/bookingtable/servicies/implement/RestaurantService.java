package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

	@Override
	public List<RestaurantDto> getAllRestaurants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestaurantDto getRestaurantById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createRestaurant(RestaurantDto restaurantDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRestaurant(String id, RestaurantDto restaurantDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRestaurant(String id) {
		// TODO Auto-generated method stub
		return false;
	}
   
}
