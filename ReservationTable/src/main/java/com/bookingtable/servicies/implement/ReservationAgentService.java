package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.servicies.IReservationAgentService;
@Service
public class ReservationAgentService implements IReservationAgentService {

	@Override
	public List<ReservationAgentDto> getAllReservationAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationAgentDto getReservationAgentById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createReservationAgent(ReservationAgentDto reservationAgentDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReservationAgent(String id, ReservationAgentDto reservationAgentDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReservationAgent(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
