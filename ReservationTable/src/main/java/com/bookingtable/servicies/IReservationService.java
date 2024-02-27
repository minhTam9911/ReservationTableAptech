package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ReservationDto;

public interface IReservationService {
 
	public List<ReservationDto> getAllReservations();

    public ReservationDto getReservationById(String id);

    public boolean createReservation(ReservationDto reservationDto) ;
    public boolean updateReservation(String id, ReservationDto reservationDto);

    public boolean deleteReservation(String id);
}
