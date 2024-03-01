package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.models.DinnerTableType;

import java.util.stream.Collectors;

public class DinnerTableTypeMapper {
    public static DinnerTableType mapToModel(DinnerTableTypeDto dinnerTableTypeDto) {
        return DinnerTableType.builder()
                .id(dinnerTableTypeDto.getId())
                .capacity(dinnerTableTypeDto.getCapacity())
                .type(dinnerTableTypeDto.getType())
                .description(dinnerTableTypeDto.getDescription())
                .dinnerTables(dinnerTableTypeDto.getDinnerTablesDto()
                        .stream()
                        .map(DinnerTableMapper::mapToModel)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static DinnerTableTypeDto mapToDto(DinnerTableType dinnerTableType) {
        return DinnerTableTypeDto.builder()
                .id(dinnerTableType.getId())
                .capacity(dinnerTableType.getCapacity())
                .type(dinnerTableType.getType())
                .description(dinnerTableType.getDescription())
                .dinnerTablesDto(dinnerTableType.getDinnerTables()
                        .stream()
                        .map(DinnerTableMapper::mapToDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}
