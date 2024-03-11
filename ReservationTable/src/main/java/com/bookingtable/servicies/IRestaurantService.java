package com.bookingtable.servicies;

import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.models.Restaurant;

import java.util.List;

public interface IRestaurantService {
    public List<RestaurantDto> getAllRestaurants();
    public List<RestaurantDto> getAllRestaurantsForAgent(String idAgent);
    public List<RestaurantDto> getAllRestaurantsWithCategory(Integer categoryId);
    public RestaurantDto getRestaurantById(String id);
    public RestaurantDto getRestaurantById(String id, String idAgent);
    public ResultResponse<RestaurantDto> createRestaurant(RestaurantDto restaurantDto, String emailCreatedBy) ;
    public ResultResponse<RestaurantDto> updateRestaurant(String id, RestaurantDto restaurantDto,String emailCreatedBy);

    public ResultResponse<RestaurantDto> deleteRestaurant(String id, String emailCreateBy);


}
