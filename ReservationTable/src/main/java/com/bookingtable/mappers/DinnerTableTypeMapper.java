package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.models.DinnerTableType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DinnerTableTypeMapper {
    public static DinnerTableType mapToModel(DinnerTableTypeDto dinnerTableTypeDto) {
        return DinnerTableType.builder()
                .id(dinnerTableTypeDto.getId())
                .capacity(dinnerTableTypeDto.getCapacity())
                .price(dinnerTableTypeDto.getPrice())
                .type(dinnerTableTypeDto.getType())
                .description(dinnerTableTypeDto.getDescription())
                .build();
    }

    public static DinnerTableTypeDto mapToDto(DinnerTableType dinnerTableType) {
        return DinnerTableTypeDto.builder()
                .id(dinnerTableType.getId())
                .capacity(dinnerTableType.getCapacity())
                .price(dinnerTableType.getPrice())
                .type(dinnerTableType.getType())
                .description(dinnerTableType.getDescription())
                .build();
    }
}
