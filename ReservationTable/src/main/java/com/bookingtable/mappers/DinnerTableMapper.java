package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.models.DinnerTable;

import java.util.stream.Collectors;

public class DinnerTableMapper {
    public static DinnerTable mapToModel(DinnerTableDto dinnerTableDto) {
        return DinnerTable.builder()
                .id(dinnerTableDto.getId())
                .quantity(dinnerTableDto.getQuantity())
                .status(dinnerTableDto.getStatus())
                .dinnerTableType(DinnerTableTypeMapper.mapToModel(dinnerTableDto.getDinnerTableTypeDto()))
                .restaurant(RestaurantMapper.mapToModel(dinnerTableDto.getRestaurantDto()))
                .images(dinnerTableDto.getImagesDto().stream().map(ImageMapper::mapToModel).collect(Collectors.toSet()))
                .build();
    }

    public static DinnerTableDto mapToDto(DinnerTable dinnerTable) {
        return DinnerTableDto.builder()
                .id(dinnerTable.getId())
                .quantity(dinnerTable.getQuantity())
                .status(dinnerTable.getStatus())
                .dinnerTableTypeDto(DinnerTableTypeMapper.mapToDto(dinnerTable.getDinnerTableType()))
                .restaurantDto(RestaurantMapper.mapToDto(dinnerTable.getRestaurant()))
                .imagesDto(dinnerTable.getImages().stream().map(ImageMapper::mapToDto).collect(Collectors.toSet()))
                .build();
    }
}
