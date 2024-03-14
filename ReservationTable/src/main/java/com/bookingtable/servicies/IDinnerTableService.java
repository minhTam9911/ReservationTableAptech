package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.RestaurantMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IDinnerTableService {
    public List<DinnerTableDto> getAllDinnerTables();
    public List<DinnerTableDto> getAllCategory(Integer id);
    public DinnerTableDto getDinnerTableById(Integer id);

    public ResultResponse<String> createDinnerTable(DinnerTableDto dinnerTableDto) ;
    public ResultResponse<String> updateDinnerTable(Integer id,DinnerTableDto dinnerTableDto);

    public ResultResponse<String> deleteDinnerTable(Integer id);
    public List<DinnerTableDto> getDinnerTablesByType(DinnerTableTypeDto dinnerTableTypeDto);
    public List<DinnerTableDto> getAllDinnerTablesForRestaurant(String restaunrantId);
}
