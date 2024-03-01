package com.bookingtable.mappers;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.models.ReservationAgent;
import com.bookingtable.models.System;

public class ReservationAgentMapper {
	public static  ReservationAgent mapToModel(ReservationAgentDto reservationAgentDto) {
		return ReservationAgent.builder()
				.id(reservationAgentDto.getId())
				.fullName(reservationAgentDto.getFullName())
				.city(reservationAgentDto.getCity())
				.district(reservationAgentDto.getDistrict())
				.ward(reservationAgentDto.getWard())
				.homePhoneNumber(reservationAgentDto.getHomePhoneNumber())
				.address(reservationAgentDto.getAddress())
				.email(reservationAgentDto.getEmail())
				.password(reservationAgentDto.getPassword())
				.cellularPhoneNumber(reservationAgentDto.getCellularPhoneNumber())
				.created(reservationAgentDto.getCreated())
				.updated(reservationAgentDto.getUpdated())
				.createBy(SystemMapper.mapToModel(reservationAgentDto.getCreateBy()))
				.role(RoleMapper.mapToModel(reservationAgentDto.getRoleDto()))
				.build();
	}
	public static  ReservationAgentDto mapToDto(ReservationAgent reservationAgent) {
		return ReservationAgentDto.builder()
				.id(reservationAgent.getId())
				.fullName(reservationAgent.getFullName())
				.city(reservationAgent.getCity())
				.district(reservationAgent.getDistrict())
				.ward(reservationAgent.getWard())
				.homePhoneNumber(reservationAgent.getHomePhoneNumber())
				.address(reservationAgent.getAddress())
				.email(reservationAgent.getEmail())
				.password(reservationAgent.getPassword())
				.cellularPhoneNumber(reservationAgent.getCellularPhoneNumber())
				.created(reservationAgent.getCreated())
				.updated(reservationAgent.getUpdated())
				.roleDto(RoleMapper.mapToDto(reservationAgent.getRole()))
				.createBy(SystemMapper.mapToDto(reservationAgent.getCreateBy()))
				.build();
	}
}
