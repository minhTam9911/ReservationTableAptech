package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ResultResponse;

public interface IReceptionistService {
	public List<ReceptionistDto> getAllReceptionist();

    public ReceptionistDto getReceptionistById(String id);

    public  ResultResponse<ReceptionistDto> createReceptionist(ReceptionistDto  restaurantDto) ;
    public  ResultResponse<ReceptionistDto> updateReceptionist(String id, ReceptionistDto receptionistDto);

    public  ResultResponse<ReceptionistDto> deleteReceptionist(String id);
    public boolean changeStatus(String id);
}
