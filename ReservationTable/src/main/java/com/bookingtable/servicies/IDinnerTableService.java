package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ResultResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IDinnerTableService {
    public List<DinnerTableDto> getAllDinnerTables();

    public DinnerTableDto getDinnerTableById(Integer id);

    public ResultResponse<DinnerTableDto> createDinnerTable(DinnerTableDto dinnerTableDto) ;
    public ResultResponse<DinnerTableDto> updateDinnerTable(Integer id,DinnerTableDto dinnerTableDto);

    public ResultResponse<DinnerTableDto> deleteDinnerTable(Integer id);
    public List<DinnerTableDto> getDinnerTablesByType(DinnerTableTypeDto dinnerTableTypeDto);
}
