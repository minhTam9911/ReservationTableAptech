package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.RestaurantDto;

import java.util.List;

public interface IDinnerTableService {
    public List<DinnerTableDto> getAllDinnerTables();

    public DinnerTableDto getDinnerTableById(Integer id);

    public boolean createDinnerTable(DinnerTableDto dinnerTableDto) ;
    public boolean updateDinnerTable(DinnerTableDto dinnerTableDto);

    public boolean deleteDinnerTable(Integer id);
}
