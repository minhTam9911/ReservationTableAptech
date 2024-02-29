package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.SystemDto;

public interface ISystemService {

	public List<SystemDto> getAllSystems();

    public SystemDto getSystemsById(UUID id);

    public boolean insertSystem(SystemDto systemDto) ;
    public boolean updateSystem(UUID id, SystemDto systemDto);

    public boolean deleteSystem(UUID id);
	
}
