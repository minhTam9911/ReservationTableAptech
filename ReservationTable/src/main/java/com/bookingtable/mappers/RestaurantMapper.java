package com.bookingtable.mappers;


import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.models.Image;
import com.bookingtable.models.ReservationAgent;
import com.bookingtable.models.Restaurant;

import java.util.HashSet;
import java.util.stream.Collectors;

public class RestaurantMapper {
    public static Restaurant mapToModel(RestaurantDto restaurantDto) {
        return Restaurant.builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .mainPhoneNumber(restaurantDto.getMainPhoneNumber())
                .faxNumber(restaurantDto.getFaxNumber())
                .tollFreeNumber(restaurantDto.getTotalFreeNumber())
                .companyMail(restaurantDto.getCompanyMail())
                .website(restaurantDto.getWebsite())
                .city(restaurantDto.getCity())
                .district(restaurantDto.getDistrict())
                .ward(restaurantDto.getWard())
                .active(restaurantDto.isActive())
                .status(restaurantDto.getStatus())
                .shortDescription(restaurantDto.getShortDescription())
                .description(restaurantDto.getDescription())
                .categoryRestaurant(CategoryRestaurantMapper.mapToModel(restaurantDto.getCategoryRetaurantDto()))
                .address(restaurantDto.getAddress())
                .images(new HashSet<Image>(restaurantDto.getImagesDto().stream().map(i->ImageMapper.mapToModel(i)).collect(Collectors.toList())))
                .build();
    }

    public static RestaurantDto mapToDto(Restaurant restaurant) {
    	 return RestaurantDto.builder()
                 .id(restaurant.getId())
                 .name(restaurant.getName())
                 .mainPhoneNumber(restaurant.getMainPhoneNumber())
                 .faxNumber(restaurant.getFaxNumber())
                 .totalFreeNumber(restaurant.getTollFreeNumber())
                 .companyMail(restaurant.getCompanyMail())
                 .website(restaurant.getWebsite())
                 .city(restaurant.getCity())
                 .status(restaurant.getStatus())
                 .active(restaurant.isActive())
                 .district(restaurant.getDistrict())
                 .shortDescription(restaurant.getShortDescription())
                 .ward(restaurant.getWard())
                 .created(restaurant.getCreated())
                 .updated(restaurant.getUpdated())
                 .description(restaurant.getDescription())
                 .categoryRetaurantDto(CategoryRestaurantMapper.mapToDto(restaurant.getCategoryRestaurant()))
                 .address(restaurant.getAddress())
                 .imagesDto(restaurant.getImages().stream().map(i->ImageMapper.mapToDto(i)).collect(Collectors.toList()))
                 .build();
    }
}
