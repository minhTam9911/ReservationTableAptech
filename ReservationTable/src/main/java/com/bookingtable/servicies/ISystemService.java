package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;

public interface ISystemService {

	public List<SystemDto> getAllSystems();

    public SystemDto getSystemsById(UUID id);

    public ResultResponse<SystemDto> insertSystem(SystemDto systemDto) ;
    public  ResultResponse<SystemDto> updateSystem(UUID id, SystemDto systemDto);

    public  ResultResponse<SystemDto> deleteSystem(UUID id);
	
}
