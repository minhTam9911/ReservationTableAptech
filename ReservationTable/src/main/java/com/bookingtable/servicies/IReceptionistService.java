package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ReceptionistDto;

public interface IReceptionistService {
	public List<ReceptionistDto> getAllReceptionist();

    public ReceptionistDto getReceptionistById(String id);

    public boolean createReceptionist(ReceptionistDto  restaurantDto) ;
    public boolean updateReceptionist(String id, ReceptionistDto receptionistDto);

    public boolean deleteReceptionist(String id);
}
