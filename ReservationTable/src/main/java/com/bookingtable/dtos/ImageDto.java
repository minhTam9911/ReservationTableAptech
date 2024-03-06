package com.bookingtable.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageDto {
    
    private Integer id;
    
    private String path;

    private DinnerTableDto dinnerTableDto;

    private RestaurantDto restaurantDto;

	public ImageDto(String path) {
		super();
		this.path = path;
	}
    
    
    
}
