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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	
	private String city;
	
	private String district;
	
	private String ward;
	
    @NotEmpty
	private String address;
	private boolean status;
	
    @NotEmpty
    @Email
	private String email;
	
    @NotEmpty
    @Size(min = 8)
	private String homePhoneNumber;
	
    @Min(value =1)
	private int totalRestaurant;

    @NotEmpty
    @Size(min = 8)
	private String cellularPhoneNumber;
	
	private String password;
	
	private LocalDate created;
	
	private LocalDate updated;
	
	private RoleDto roleDto;
	
	 private Collection<ReceptionistDto> receptionistsDto;
	 
	 private SystemDto createBy;

	public ReservationAgentDto( String email) {
		super();
		this.email = email;
	}
	
}
