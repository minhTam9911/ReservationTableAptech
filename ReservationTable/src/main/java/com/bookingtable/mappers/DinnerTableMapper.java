package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.models.DinnerTable;

import java.util.stream.Collectors;

public class DinnerTableMapper {
    public static DinnerTable mapToModel(DinnerTableDto dinnerTableDto) {
        return DinnerTable.builder()
                .id(dinnerTableDto.getId())
                .quantity(dinnerTableDto.getQuantity())
                .status(dinnerTableDto.getStatus())
                .images(dinnerTableDto.getImagesDto()
                        .stream().map(i->ImageMapper.mapToModel(i))
                        .collect(Collectors.toList()))
                .dinnerTableType(DinnerTableTypeMapper.mapToModel(dinnerTableDto.getDinnerTableTypeDto()))
                .restaurant(RestaurantMapper.mapToModel(dinnerTableDto.getRestaurantDto()))
                .build();
    }

    public static DinnerTableDto mapToDto(DinnerTable dinnerTable) {
        return DinnerTableDto.builder()
                .id(dinnerTable.getId())
                .status(dinnerTable.getStatus())
                .quantity(dinnerTable.getQuantity())
                .imagesDto(dinnerTable.getImages()
                        .stream().map(i->ImageMapper.mapToDto(i))
                        .collect(Collectors.toList()))
                .dinnerTableTypeDto(DinnerTableTypeDto.builder() // Use a simplified version here
                        .id(dinnerTable.getDinnerTableType().getId())
                        .type(dinnerTable.getDinnerTableType().getType())
                        .capacity(dinnerTable.getDinnerTableType().getCapacity())
                        .description(dinnerTable.getDinnerTableType().getDescription())
                        .build())
                .restaurantDto(RestaurantMapper.mapToDto(dinnerTable.getRestaurant()))
                .build();
    }
}
