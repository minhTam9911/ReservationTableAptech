package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ReservationAgentDto;

public interface IReservationAgentService {

	public List<ReservationAgentDto> getAllReservationAgents();

    public ReservationAgentDto getReservationAgentById(UUID id);

    public boolean createReservationAgent(ReservationAgentDto reservationAgentDto) ;
    public boolean updateReservationAgent(UUID id, ReservationAgentDto reservationAgentDto);

    public boolean deleteReservationAgent(UUID id);
	
}
