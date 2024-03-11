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

import java.util.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinnerTableDto {
	
    private Integer id;
    @Min(value = 1,message = "Quantity must higher one")
    @NotNull(message = "Quantity table cannot be null")
    private int quantity;

    private int currentQuantity;
    @NotEmpty(message = "Status cannot be empty")
    private String status;
    @NotNull(message = "Dinner table cannot be null, please select dinner table")
    private DinnerTableTypeDto dinnerTableTypeDto;

    private List<DinnerTableTypeDto> dinnerTableTypeList;
    private Integer dinnerTableTypeDtoId;
    private String restaurantDtoId;
    @NotNull(message = "Restaurant table cannot be null, please select restaurant")
    private RestaurantDto restaurantDto;
    private List<RestaurantDto> restaurantList;
    private List<ImageDto> imagesDto = new ArrayList<>();
    @NotNull(message = "Image cannot be null, please select image")
    private ImageDto imageDto;
}
