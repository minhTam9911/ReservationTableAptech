package com.bookingtable.mappers;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.models.Reservation;

public class ReservationMapper {
	public static  Reservation mapToModel(ReservationDto reservationDto) {
		return Reservation.builder()
				.id(reservationDto.getId())
				.restaurant(RestaurantMapper.mapToModel(reservationDto.getRestaurantDto()))
				.customer(CustomerMapper.mapToModel(reservationDto.getCustomerDto()))
				.dinnerTable(DinnerTableMapper.mapToModel(reservationDto.getDinnerTableDto()))
				.created(reservationDto.getCreated())
				.numberOfPeople(reservationDto.getNumberOfPeople())
				.bookingDate(reservationDto.getBookingDate())
				.bookingTime(reservationDto.getBookingTime())
				.reservationStatus(ReservationStatusMapper.mapToModel(reservationDto.getReservationStatusDto()))
				.quantity(reservationDto.getQuantity())
				.build();
	}
	public static  ReservationDto mapToDto(Reservation reservation) {
		return ReservationDto.builder()
				.id(reservation.getId())
				.customerDto(CustomerMapper.mapToDto(reservation.getCustomer()))
				.restaurantDto(RestaurantMapper.mapToDto(reservation.getRestaurant()))
				.dinnerTableDto(DinnerTableMapper.mapToDto(reservation.getDinnerTable()))
				.created(reservation.getCreated())
				.quantity(reservation.getQuantity())
				.bookingDate(reservation.getBookingDate())
				.bookingTime(reservation.getBookingTime())
				.reservationStatusDto(ReservationStatusMapper.mapToDto(reservation.getReservationStatus()))
				.build();
	}
}
