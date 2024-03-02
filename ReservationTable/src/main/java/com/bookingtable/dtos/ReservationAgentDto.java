package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

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

public class ReservationAgentDto {

	
	private UUID id;
	@NotNull
    @NotEmpty
	private String fullName;
	@NotNull
    @NotEmpty
	private String city;
	@NotNull
    @NotEmpty
	private String district;
	@NotNull
    @NotEmpty
	private String ward;
	@NotNull
    @NotEmpty
	private String address;
	@NotNull
    @NotEmpty
	private String email;
	@NotNull
    @NotEmpty
	private String homePhoneNumber;
	
	private int totalRestaurant;
	@NotNull
    @NotEmpty
	private String cellularPhoneNumber;
	@NotNull
    @NotEmpty
	private String password;
	
	private LocalDate created;
	
	private LocalDate updated;
	
	private RoleDto roleDto;
	
	 private Collection<ReceptionistDto> receptionistsDto;
	 
	 private SystemDto createBy;
	
}
