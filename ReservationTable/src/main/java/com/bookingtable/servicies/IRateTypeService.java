package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.RateTypeDto;
import com.bookingtable.models.RateType;

public interface IRateTypeService {
	public List<RateTypeDto> getAllRateTypes();

    public RateTypeDto getRateTypeById(Integer id);

    public boolean createRateType(RateTypeDto rateTypeDto) ;
    public boolean updateRateType(Integer id, RateTypeDto rateTypeDto);

    public boolean deleteRateType(Integer id);
}
