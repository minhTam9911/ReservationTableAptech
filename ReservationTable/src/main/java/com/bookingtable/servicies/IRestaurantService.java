package com.bookingtable.servicies;

import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.models.Restaurant;

import java.util.List;

public interface IRestaurantService {
    public List<RestaurantDto> getAllRestaurants();
    public List<RestaurantDto> getAllRestaurantsForAgent(String idAgent);

    public RestaurantDto getRestaurantById(String id);
    public RestaurantDto getRestaurantById(String id, String idAgent);
    public boolean createRestaurant(RestaurantDto restaurantDto) ;
    public boolean updateRestaurant(String id, RestaurantDto restaurantDto);

    public boolean deleteRestaurant(String id);
}
