package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.dtos.ReservationStatusDto;

public interface IReservationStatusService {
 
	public List<ReservationStatusDto> getAllReservationStatuses();

    public ReservationStatusDto getReservationStatusById(Integer id);

    public boolean createReservationStatus(ReservationStatusDto reservationDto) ;
    public boolean updateReservationStatus(Integer id, ReservationStatusDto reservationDto);

    public boolean deleteReservationStatus(Integer id);
}
