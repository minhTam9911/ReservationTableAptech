package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ReceptionistDto;

public interface IReceptionistService {
	public List<ReceptionistDto> getAllReceptionistts();

    public ReceptionistDto getReceptionistById(String id);

    public boolean createReceptionist(ReceptionistDto  restaurantDto) ;
    public boolean updateReceptionist(String id, ReceptionistDto receptionistDto);

    public boolean deleteReceptionist(String id);
}
