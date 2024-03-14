package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;

public interface ISystemService {

	public List<SystemDto> getAllSystems();

    public SystemDto getSystemsById(String id);

    public ResultResponse<String> insertSystem(SystemDto systemDto) ;
    public  ResultResponse<String> updateSystem(String id, SystemDto systemDto);

    public  ResultResponse<String> deleteSystem(String id);
    public boolean changeStatus(String id);
	
}
