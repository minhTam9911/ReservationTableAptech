package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.ReservationAgentDto;

public interface IReservationAgentService {

	public List<ReservationAgentDto> getAllReservationAgents();

    public ReservationAgentDto getReservationAgentById(String id);

    public boolean createReservationAgent(ReservationAgentDto reservationAgentDto) ;
    public boolean updateReservationAgent(String id, ReservationAgentDto reservationAgentDto);

    public boolean deleteReservationAgent(String id);
	
}
