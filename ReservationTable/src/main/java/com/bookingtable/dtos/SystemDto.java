package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bookingtable.models.Role;


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
	@NotNull
    @NotEmpty
    @Email
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
}
