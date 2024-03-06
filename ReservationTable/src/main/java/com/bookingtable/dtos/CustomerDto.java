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

	@NotEmpty
	private String fullname;

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	private String address;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
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
