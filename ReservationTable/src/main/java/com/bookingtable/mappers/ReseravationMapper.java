package com.bookingtable.mappers;

import java.util.Collection;
import java.util.stream.Collectors;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.models.Reservation;

public class ReseravationMapper {
	public static  Reservation mapToModel(ReservationDto reservationDto) {
		return Reservation.builder()
				.id(reservationDto.getId())
				.restaurant(RestaurantMapper.mapToModel(reservationDto.getRestaurantDto()))
				.guest(GuestMapper.mapToModel(reservationDto.getGuestDto()))
				.dinnerTable(reservationDto.getDinnerTableDto().stream().map(i->DinnerTableMapper.mapToModel(i)).collect(Collectors.toList()))
				.created(reservationDto.getCreated())
				.tableCount(reservationDto.getTableCount())
				.numberOfPepole(reservationDto.getNumberOfPepole())
				.floor(reservationDto.getFloor())
				.bookingDate(reservationDto.getBookingDate())
				.bookingTime(reservationDto.getBookingTime())
				.status(reservationDto.isStatus())
				.build();
	}
	public static  ReservationDto mapToDto(Reservation reservation) {
		return ReservationDto.builder()
				.id(reservation.getId())
				.guestDto(GuestMapper.mapToDto(reservation.getGuest()))
				.restaurantDto(RestaurantMapper.mapToDto(reservation.getRestaurant()))
				.dinnerTableDto(reservation.getDinnerTable().stream().map(i->DinnerTableMapper.mapToDto(i)).collect(Collectors.toList()))
				.created(reservation.getCreated())
				.tableCount(reservation.getTableCount())
				.numberOfPepole(reservation.getNumberOfPepole())
				.floor(reservation.getFloor())
				.bookingDate(reservation.getBookingDate())
				.bookingTime(reservation.getBookingTime())
				.status(reservation.isStatus())
				.build();
	}
}
