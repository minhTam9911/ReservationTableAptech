package com.bookingtable.mappers;

import com.bookingtable.dtos.CategoryRestaurantDto;
import com.bookingtable.models.CategoryRestaurant;

public class CategoryRestaurantMapper {

	public static CategoryRestaurant mapToModel(CategoryRestaurantDto categoryRestaurantDto) {
		return CategoryRestaurant.builder()
				.id(categoryRestaurantDto.getId())
				.name(categoryRestaurantDto.getName())
				
				.build();
	}
	public static CategoryRestaurantDto mapToDto(CategoryRestaurant categoryRestaurant) {
		return CategoryRestaurantDto.builder()
				.id(categoryRestaurant.getId())
				.name(categoryRestaurant.getName())
				
				.build();
	}
	
}
