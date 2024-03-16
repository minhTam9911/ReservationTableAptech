package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bookingtable.models.Role;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
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
public class SystemDto {
	
	private String id;
	
    @NotEmpty
	private String fullname;
	
    @NotEmpty
	@Pattern(regexp = "^[0-9]+$")
	private String phoneNumber;

	private String image;
    @NotEmpty
	private String address;
    private String detailAddress;
	
    @NotEmpty
    @Email
	private String email;
	
	private String password;
	
	private boolean gender;
	
	private boolean status;
	
	@NotNull
	private LocalDate dateOfBirth;
	
	private LocalDate created;
	
	private LocalDate updated;
	
	private RoleDto roleDto;
	
	//@NotNull
	//private Integer roleId;
	//private List<RoleDto> roleList;
	
	private List<ReservationAgentDto> reservationAgentDtos;

	public SystemDto( String email) {
		super();
		this.email = email;
	}
	
}
