package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.List;

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
public class CustomerDto {

	private String id;

	@NotEmpty(message = "Full name cannot be empty")
	private String fullname;

	@NotEmpty(message = "Phone number cannot be null")
	private String phoneNumber;

	@NotEmpty(message = "Address cannot be null")
	private String address;

	@NotEmpty(message = "Email cannot be null")
	private String email;

	@NotEmpty(message = "Password cannot be null")
	private String password;

	private boolean gender;

	@NotNull
	private LocalDate dateOfBirth;
	private LocalDate created;
	private LocalDate updated;
	private RoleDto roleDto;
	private Integer roleId;
	private List<RoleDto> roleList;
	public CustomerDto(String email) {
		super();
		this.email = email;
	}
}
