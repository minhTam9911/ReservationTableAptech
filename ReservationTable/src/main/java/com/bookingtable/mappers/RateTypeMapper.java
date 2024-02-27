package com.bookingtable.mappers;

import com.bookingtable.dtos.RateTypeDto;
import com.bookingtable.models.RateType;

public class RateTypeMapper {
    public static RateType mapToModel(RateTypeDto rateTypeDto) {
        return RateType.builder()
                .id(rateTypeDto.getId())
                .type(rateTypeDto.getType())
                .description(rateTypeDto.getDescription())
                .build();
    }

    public static RateTypeDto mapToDto(RateType rateType) {
        return RateTypeDto.builder()
                .id(rateType.getId())
                .type(rateType.getType())
                .description(rateType.getDescription())
                .build();
    }
}
