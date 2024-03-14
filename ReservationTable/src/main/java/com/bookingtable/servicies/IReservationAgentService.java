package com.bookingtable.servicies;

import java.util.List;
import java.util.UUID;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;

public interface IReservationAgentService {

	public List<ReservationAgentDto> getAllReservationAgents();

    public ReservationAgentDto getReservationAgentById(String id);

    public ResultResponse<String> createReservationAgent(ReservationAgentDto reservationAgentDto, String emailCreatedBy) ;
    public ResultResponse<String> updateReservationAgent(String id, ReservationAgentDto reservationAgentDto);

    public ResultResponse<String> deleteReservationAgent(String id);
    public boolean changeStatus(String id);
	
	
}
