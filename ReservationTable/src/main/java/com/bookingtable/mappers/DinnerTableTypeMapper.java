package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;

import java.util.Collections;
import java.util.stream.Collectors;

public class DinnerTableTypeMapper {
    public static DinnerTableType mapToModel(DinnerTableTypeDto dinnerTableTypeDto) {
        if (dinnerTableTypeDto.getDinnerTablesDto() == null) {
            dinnerTableTypeDto.setDinnerTablesDto(Collections.emptyList()); // or another appropriate default value
        }

        return DinnerTableType.builder()
                .id(dinnerTableTypeDto.getId())
                .capacity(dinnerTableTypeDto.getCapacity())
                .type(dinnerTableTypeDto.getType())
                .description(dinnerTableTypeDto.getDescription())
                .dinnerTables(dinnerTableTypeDto.getDinnerTablesDto()
                        .stream()
                        .map(DinnerTableMapper::mapToModel)
                        .collect(Collectors.toList()))
                .build();
    }

    public static DinnerTableTypeDto mapToDto(DinnerTableType dinnerTableType) {
        return DinnerTableTypeDto.builder()
                .id(dinnerTableType.getId())
                .type(dinnerTableType.getType())
                .capacity(dinnerTableType.getCapacity())
                .description(dinnerTableType.getDescription())
                .dinnerTablesDto(dinnerTableType.getDinnerTables()
                        .stream()
                        .map(DinnerTableMapper::mapToDto) // Use the lite version here
                        .collect(Collectors.toList()))
                .build();
    }
}
