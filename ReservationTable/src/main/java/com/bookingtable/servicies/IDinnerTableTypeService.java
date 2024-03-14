package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.servicies.implement.DinnerTableTypeService;

import java.util.List;

public interface IDinnerTableTypeService{
    public List<DinnerTableTypeDto> getAllDinnerTablesType();

    public DinnerTableTypeDto getDinnerTableTypeById(Integer id);

    public ResultResponse<String> createDinnerTableType(DinnerTableTypeDto dinnerTableTypeDto) ;
    public ResultResponse<String> updateDinnerTableType(Integer id, DinnerTableTypeDto dinnerTableTypeDto);

    public ResultResponse<String> deleteDinnerTableType(Integer id);
}
