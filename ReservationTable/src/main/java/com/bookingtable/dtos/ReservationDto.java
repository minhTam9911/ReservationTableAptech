package com.bookingtable.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	
	private GuestDto guestDto;
	
	private Collection<DinnerTableDto> dinnerTableDto;
	
	private LocalDateTime created;
	@NotNull
    @NotEmpty
	private int tableCount;
	@NotNull
    @NotEmpty
	private int numberOfPepole;
	
	private int floor;
	
	private LocalDate bookingDate;
	
	private LocalTime bookingTime;
	
	private boolean status;
}
