package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.List;

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

	private String fullname;

	private String phoneNumber;

	private String address;

	private String email;

	private String password;

	private boolean gender;

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
