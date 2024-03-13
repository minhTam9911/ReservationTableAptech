package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.models.Reservation;

public interface IReservationService {
 
	public List<ReservationDto> getAllReservation();

    public ReservationDto getReservationById(String id);

    public boolean createReservation(ReservationDto reservationDto) ;
    public boolean updateReservation(String id,Integer status, ReservationDto reservationDto);

    public boolean deleteReservation(String id);
    public List<Reservation> getAllReservationForReceptionist(String emailReception);
    public boolean	changeReservationStatusConfirmed(String id);
    public boolean	changeReservationStatusFinnished(String id);
}
