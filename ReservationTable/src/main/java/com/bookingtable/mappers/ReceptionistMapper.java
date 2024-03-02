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
				.dateOfBirth(receptionistDto.getDateOfBirth())
				.restaurant(RestaurantMapper.mapToModel(receptionistDto.getRestaurantDto()))
				.createBy(ReservationAgentMapper.mapToModel(receptionistDto.getReservationAgentDto()))
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
				.gender(receptionist.isGender())
				.created(receptionist.getCreated())
				.updated(receptionist.getUpdated())
				.dateOfBirth(receptionist.getDateOfBirth())
				.restaurantDto(RestaurantMapper.mapToDto(receptionist.getRestaurant()))
				.reservationAgentDto(ReservationAgentMapper.mapToDto(receptionist.getCreateBy()))
				.roleDto(RoleMapper.mapToDto(receptionist.getRole()))
				.build();
	}
}
