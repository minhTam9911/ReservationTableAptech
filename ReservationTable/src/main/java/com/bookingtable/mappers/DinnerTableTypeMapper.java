package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;

import java.util.stream.Collectors;

public class DinnerTableTypeMapper {
    public static DinnerTableType mapToModel(DinnerTableTypeDto dinnerTableTypeDto) {
        return DinnerTableType.builder()
                .id(dinnerTableTypeDto.getId())
                .capacity(dinnerTableTypeDto.getCapacity())
                .description(dinnerTableTypeDto.getDescription())
                .dinnerTables(dinnerTableTypeDto.getDinnerTablesDto().
                        stream().map(i->DinnerTableMapper.mapToModel(i))
                        .collect(Collectors.toList()))
                .build();
    }

    public static DinnerTableTypeDto mapToDto(DinnerTableType dinnerTableType) {
        return DinnerTableTypeDto.builder()
                .id(dinnerTableType.getId())
                .capacity(dinnerTableType.getCapacity())
                .description(dinnerTableType.getDescription())
                .dinnerTablesDto(dinnerTableType.getDinnerTables().
                        stream().map(i->DinnerTableMapper.mapToDto(i))
                        .collect(Collectors.toList()))
                .build();}
}
