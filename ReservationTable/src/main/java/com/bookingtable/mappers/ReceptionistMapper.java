package com.bookingtable.mappers;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.models.Receptionist;

public class ReceptionistMapper {

	public static  Receptionist mapToModel(ReceptionistDto receptionistDto) {
		return Receptionist.builder()
				.id(receptionistDto.getId())
				.fullname(receptionistDto.getFullname())
				.phoneNumber(receptionistDto.getPhoneNumber())
				.address(receptionistDto.getAddress())
				.email(receptionistDto.getEmail())
				.password(receptionistDto.getPassword())
				.gender(receptionistDto.isGender())
				.created(receptionistDto.getCreated())
				.updated(receptionistDto.getUpdated())
				.image(receptionistDto.getImage())
				.dateOfBirth(receptionistDto.getDateOfBirth())
				.restaurant(RestaurantMapper.mapToModel(receptionistDto.getRestaurantDto()))
				.reservationAgent(receptionistDto.getReservationAgentDto() == null?null: ReservationAgentMapper.mapToModel(receptionistDto.getReservationAgentDto()))
				.role(RoleMapper.mapToModel(receptionistDto.getRoleDto()))
				.build();
	}
	public static  ReceptionistDto mapToDto(Receptionist receptionist) {
		return ReceptionistDto.builder()
				.id(receptionist.getId())
				.fullname(receptionist.getFullname())
				.phoneNumber(receptionist.getPhoneNumber())
				.address(receptionist.getAddress())
				.email(receptionist.getEmail())
				.password(receptionist.getPassword())
				.image(receptionist.getImage())
				.gender(receptionist.isGender())
				.status(receptionist.isStatus())
				.created(receptionist.getCreated())
				.updated(receptionist.getUpdated())
				.dateOfBirth(receptionist.getDateOfBirth())
				.detailAddress(receptionist.getAddress())
				.restaurantDto(RestaurantMapper.mapToDto(receptionist.getRestaurant()))
				.reservationAgentDto(receptionist.getReservationAgent()==null? null: ReservationAgentMapper.mapToDto(receptionist.getReservationAgent()))
				.roleDto(RoleMapper.mapToDto(receptionist.getRole()))
				.build();
	}
}
