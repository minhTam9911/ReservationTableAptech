package com.bookingtable.dtos;

import com.bookingtable.models.DinnerTableType;

import com.bookingtable.models.Image;
import com.bookingtable.models.Restaurant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinnerTableDto {
	
    private Integer id;
    @NotNull
    @NotEmpty
    @Min(value = 1)
    private int quantity;
    @NotNull
    @NotEmpty
    private String status;
    
    private DinnerTableTypeDto dinnerTableTypeDto;
    
    private RestaurantDto restaurantDto;
  
    private Set<ImageDto> imagesDto = new HashSet<>();
}
