package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;

public interface IReservationAgentService {

	public List<ReservationAgentDto> getAllReservationAgents();

    public ReservationAgentDto getReservationAgentById(UUID id);

    public ResultResponse<ReservationAgentDto> createReservationAgent(ReservationAgentDto reservationAgentDto) ;
    public ResultResponse<ReservationAgentDto> updateReservationAgent(UUID id, ReservationAgentDto reservationAgentDto);

    public ResultResponse<ReservationAgentDto> deleteReservationAgent(UUID id);
	
}
