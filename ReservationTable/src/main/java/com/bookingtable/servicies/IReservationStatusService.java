package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ReservationStatusDto;
import com.bookingtable.dtos.ResultResponse;

public interface IReservationStatusService {
 
	public List<ReservationStatusDto> getAllReservationStatuses();

    public ReservationStatusDto getReservationStatusById(Integer id);

    public ResultResponse<String>  createReservationStatus(ReservationStatusDto reservationDto) ;
    public ResultResponse<String>  updateReservationStatus(Integer id, ReservationStatusDto reservationDto);

    public ResultResponse<String>  deleteReservationStatus(Integer id);
}
