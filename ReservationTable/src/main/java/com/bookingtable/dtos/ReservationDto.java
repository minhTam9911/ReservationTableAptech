package com.bookingtable.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ReservationDto {
	private String id;
	
	private RestaurantDto restaurantDto;
	
	private CustomerDto customerDto;
	
	private DinnerTableDto dinnerTableDto;
	
	private LocalDateTime created;
	@NotNull
    @NotEmpty
	private int quantity;
	@NotNull
    @NotEmpty
	private int numberOfPeople;

	private LocalDate bookingDate;
	
	private LocalTime bookingTime;
	
	private ReservationStatusDto reservationStatusDto;
}
