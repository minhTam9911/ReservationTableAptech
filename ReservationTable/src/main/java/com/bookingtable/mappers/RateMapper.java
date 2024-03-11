package com.bookingtable.mappers;

import com.bookingtable.dtos.RateDto;
import com.bookingtable.models.Rate;

public class RateMapper {
    public static Rate mapToModel(RateDto rateDto) {
        return Rate.builder()
                .id(rateDto.getId())
                .point(rateDto.getPoint())
                .comment(rateDto.getComment())
                .created(rateDto.getCreated())
                
                .dinnerTable(DinnerTableMapper.mapToModel(rateDto.getDinnerTable()))
                .build();
    }

    public static RateDto mapToDto(Rate rate) {
        return RateDto.builder()
                .id(rate.getId())
                .point(rate.getPoint())
                .comment(rate.getComment())
                .created(rate.getCreated())
               
                .dinnerTable(DinnerTableMapper.mapToDto(rate.getDinnerTable()))
                .build();
    }
}
