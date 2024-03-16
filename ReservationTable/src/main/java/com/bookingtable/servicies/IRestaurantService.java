package com.bookingtable.servicies;

import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.RestaurantMapper;
import com.bookingtable.models.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public interface IRestaurantService {
    public List<RestaurantDto> getAllRestaurants();
    public List<RestaurantDto> getAllCategory(Integer id);
    public List<RestaurantDto> getAllRestaurantsForAgent(String idAgent);
    public List<RestaurantDto> getAllRestaurantsWithCategory(Integer categoryId);
    public RestaurantDto getRestaurantById(String id);
    public RestaurantDto getRestaurantById(String id, String idAgent);
    public ResultResponse<String> createRestaurant(RestaurantDto restaurantDto, String emailCreatedBy) ;
    public ResultResponse<String> updateRestaurant(String id, RestaurantDto restaurantDto,String emailCreatedBy);

    public ResultResponse<String> deleteRestaurant(String id, String emailCreateBy);
    public  ResultResponse<String> changeStatus(String id,String emailCreatedBy);
    public List<RestaurantDto> getRestaurantByName(String name);
}
