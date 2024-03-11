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

	
	private String id;
	@NotNull(message = "Full name cannot be null")
	private String fullname;
	@NotNull(message = "Phone number cannot be null")
	private String phoneNumber;
	public ReceptionistDto( String email) {
		super();
		this.email = email;
	}
	@NotNull(message = "Address cannot be null")
	private String address;
	private boolean status;
	@NotNull(message = "Email cannot be null")
	private String email;

	@NotNull(message = "Password cannot be null")
	private String password;

	private boolean gender;

	@NotNull(message = "Date of birth cannot be null")
	private LocalDate dateOfBirth;
	
	private LocalDate created;
	
	private LocalDate updated;
	
	private RoleDto roleDto;
	
	private RestaurantDto restaurantDto;
	
	private String restaurantDtoId;
	
	
	private ReservationAgentDto reservationAgentDto;
	
}
