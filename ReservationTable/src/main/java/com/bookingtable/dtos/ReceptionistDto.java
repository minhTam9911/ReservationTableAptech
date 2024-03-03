package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bookingtable.models.ReservationAgent;
import com.bookingtable.models.Restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ReceptionistDto {

	
	private UUID id;
	@NotNull
    @NotEmpty
	private String fullname;
	@NotNull
    @NotEmpty
	private String phoneNumber;
	@NotNull
    @NotEmpty
	private String address;
	private boolean status;
	@NotNull
    @NotEmpty
	private String email;
	@NotNull
    @NotEmpty
	private String password;
	@NotNull
    @NotEmpty
	private boolean gender;
	@NotNull
    @NotEmpty
	private LocalDate dateOfBirth;
	
	private LocalDate created;
	
	private LocalDate updated;
	
	private RoleDto roleDto;
	
	private RestaurantDto restaurantDto;
	private ReservationAgentDto reservationAgentDto;
	
}
