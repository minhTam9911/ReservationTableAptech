package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.servicies.IReservationService;
@Service
public class ReservationService implements IReservationService {

	@Override
	public List<ReservationDto> getAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationDto getReservationById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createReservation(ReservationDto reservationDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReservation(String id, ReservationDto reservationDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReservation(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
