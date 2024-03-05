package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.RateTypeDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.models.RateType;

public interface IRateTypeService {
	public List<RateTypeDto> getAllRateTypes();

    public RateTypeDto getRateTypeById(Integer id);

    public ResultResponse<RateTypeDto> createRateType(RateTypeDto rateTypeDto) ;
    public ResultResponse<RateTypeDto> updateRateType(Integer id, RateTypeDto rateTypeDto);

    public ResultResponse<RateTypeDto> deleteRateType(Integer id);
}
