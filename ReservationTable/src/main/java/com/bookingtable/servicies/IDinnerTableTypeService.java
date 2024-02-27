package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.servicies.implement.DinnerTableTypeService;

import java.util.List;

public interface IDinnerTableTypeService{
    public List<DinnerTableTypeDto> getAllDinnerTablesType();

    public DinnerTableTypeDto getDinnerTableTypeById(Integer id);

    public boolean createDinnerTableType(DinnerTableTypeDto dinnerTableTypeDto) ;
    public boolean updateDinnerTableType(Integer id, DinnerTableTypeDto dinnerTableTypeDto);

    public boolean deleteDinnerTableType(Integer id);
}
