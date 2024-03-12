package com.bookingtable.dtos;

import com.bookingtable.models.Customer;
import com.bookingtable.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CollectionDto {
	
	
	private Integer id;
	
	private RestaurantDto restaurant;

	private CustomerDto customer;

	private boolean status;

}

