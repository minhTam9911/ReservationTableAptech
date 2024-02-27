package com.bookingtable.servicies;

import java.util.List;


import com.bookingtable.dtos.SystemDto;

public interface ISystemService {

	public List<SystemDto> getAllSystems();

    public SystemDto getSystemsById(String id);

    public boolean createRestaurant(SystemDto systemDto) ;
    public boolean updateSystem(String id, SystemDto systemDto);

    public boolean deleteSystem(String id);
	
}
