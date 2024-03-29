package com.bookingtable.dtos;

import com.bookingtable.models.DinnerTableType;

import com.bookingtable.models.Image;
import com.bookingtable.models.Restaurant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private int quantity;

    private int currentQuantity;
    private String status;
    private DinnerTableTypeDto dinnerTableTypeDto;

    private List<DinnerTableTypeDto> dinnerTableTypeList;
    private Integer dinnerTableTypeDtoId;
    private String restaurantDtoId;
    private RestaurantDto restaurantDto;
    private List<RestaurantDto> restaurantList;
    private List<ImageDto> imagesDto = new ArrayList<>();
    private ImageDto imageDto;
}
