package com.bookingtable.mappers;

import com.bookingtable.dtos.RestaurantStatisticalDto;
import com.bookingtable.models.RestaurantStatistical;

public class RestaurantStatisticalMapper {

	public static RestaurantStatisticalDto mapToDto(RestaurantStatistical restaurantStatistical) {
		return RestaurantStatisticalDto.builder()
				.date(restaurantStatistical.getDate())
				.totalBookinged(restaurantStatistical.getTotalBookinged())
				.totalCanceled(restaurantStatistical.getTotalCanceled())
				.totalFinished(restaurantStatistical.getTotalFinished())
				.totalDinnerTable(restaurantStatistical.getTotalDinnerTable())
				.totalCustomer(restaurantStatistical.getTotalCustomer())
				.build();
	}
	
}
