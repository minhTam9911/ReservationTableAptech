package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ResultResponse;

public interface IReceptionistService {
	public List<ReceptionistDto> getAllReceptionist(String emailCreatdBy);

    public ReceptionistDto getReceptionistById(String id,String emailCreatdBy);

    public  ResultResponse<ReceptionistDto> createReceptionist(ReceptionistDto  restaurantDto, String emailCreatedBy) ;
    public  ResultResponse<ReceptionistDto> updateReceptionist(String id, ReceptionistDto receptionistDto,String emailCreatdBy);

    public  ResultResponse<ReceptionistDto> deleteReceptionist(String id,String emailCreatdBy);
    public boolean changeStatus(String id,String emailCreatedBy);
}
