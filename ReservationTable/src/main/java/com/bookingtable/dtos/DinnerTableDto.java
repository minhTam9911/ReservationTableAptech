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
    @NotNull

    private int quantity;

    private String status;
    
    private DinnerTableTypeDto dinnerTableTypeDto;

    private List<DinnerTableTypeDto> dinnerTableTypeList;
    @NotNull
    private Integer dinnerTableTypeDtoId;
    @NotNull
    private String restaurantDtoId;
    private RestaurantDto restaurantDto;
    private List<RestaurantDto> restaurantList;

    private List<ImageDto> imagesDto;

    private ImageDto imageDto;
}
