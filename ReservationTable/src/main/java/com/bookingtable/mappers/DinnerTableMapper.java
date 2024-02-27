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
                .images(dinnerTableDto.getImagesDto()
                        .stream().map(i->ImageMapper.mapToModel(i))
                        .collect(Collectors.toList()))
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
                .build();}
}
