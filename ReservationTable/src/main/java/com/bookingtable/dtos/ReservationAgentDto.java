package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bookingtable.models.Receptionist;
import com.bookingtable.models.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ReservationAgentDto {
	private String id;

	@NotEmpty
	private String fullName;
	private String fullname;

	private String city;

	private String district;

	private String ward;

	private String image;

	@NotEmpty
	private String address;
	private boolean status;

	private String detailAddress;
	
	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Size(min = 8)
	@Pattern(regexp = "^[0-9]+$", message = "This field can only enter numbers")
	private String homePhoneNumber;
	
	
	private String phoneNumber;

	@Min(value = 1)
	private int totalRestaurant;

	@NotEmpty
	@Size(min = 8)
	@Pattern(regexp = "^[0-9]+$", message = "This field can only enter numbers")
	private String cellularPhoneNumber;

	private String password;

	private LocalDate created;

	private LocalDate updated;

	private RoleDto roleDto;

	private Collection<ReceptionistDto> receptionistsDto;

	private SystemDto createBy;

	public ReservationAgentDto(String email) {
		super();
		this.email = email;
	}

}
