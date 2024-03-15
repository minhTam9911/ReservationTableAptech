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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@NotEmpty
	private String fullname;
	@NotEmpty
	@Pattern(regexp = "^[0-9]+$",message = "This field can only enter numbers")
	private String phoneNumber;
	public ReceptionistDto( String email) {
		super();
		this.email = email;
	}
	@NotEmpty
	private String address;
	private boolean status;
	@NotEmpty
	@Email
	private String email;

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
