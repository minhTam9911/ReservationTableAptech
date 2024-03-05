package com.bookingtable.dtos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String email;
	@NotNull
	@NotEmpty
	private String password;
	@NotNull
	@NotEmpty
	private boolean gender;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate created;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate updated;
	@NotNull
	private RoleDto roleDto;

	@NotNull
	private Integer roleId;
	private List<RoleDto> roleList;
	public CustomerDto(String email) {
		super();
		this.email = email;
	}
}
