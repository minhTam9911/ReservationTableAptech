package com.bookingtable.mappers;

import java.util.stream.Collectors;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.models.Reservation;

public class ReseravationMapper {
	public static  Reservation mapToModel(ReservationDto reservationDto) {
		return Reservation.builder()
				.id(reservationDto.getId())
				.restaurant(RestaurantMapper.mapToModel(reservationDto.getRestaurantDto()))
				.customer(CustomerMapper.mapToModel(reservationDto.getCustomerDto()))
				.dinnerTable(reservationDto.getDinnerTableDto().stream().map(i->DinnerTableMapper.mapToModel(i)).collect(Collectors.toList()))
				.created(reservationDto.getCreated())
				.tableCount(reservationDto.getTableCount())
				.numberOfPepole(reservationDto.getNumberOfPepole())
				.floor(reservationDto.getFloor())
				.bookingDate(reservationDto.getBookingDate())
				.bookingTime(reservationDto.getBookingTime())
				.reservationStatus(ReservationStatusMapper.mapToModel(reservationDto.getReservationStatusDto()))
				.build();
	}
	public static  ReservationDto mapToDto(Reservation reservation) {
		return ReservationDto.builder()
				.id(reservation.getId())
				.customerDto(CustomerMapper.mapToDto(reservation.getCustomer()))
				.restaurantDto(RestaurantMapper.mapToDto(reservation.getRestaurant()))
				.dinnerTableDto(reservation.getDinnerTable().stream().map(i->DinnerTableMapper.mapToDto(i)).collect(Collectors.toList()))
				.created(reservation.getCreated())
				.tableCount(reservation.getTableCount())
				.numberOfPepole(reservation.getNumberOfPepole())
				.floor(reservation.getFloor())
				.bookingDate(reservation.getBookingDate())
				.bookingTime(reservation.getBookingTime())
				.reservationStatusDto(ReservationStatusMapper.mapToDto(reservation.getReservationStatus()))
				.build();
	}
}
