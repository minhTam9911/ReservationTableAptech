package com.bookingtable.dtos;

import java.util.ArrayList;
import java.util.Collection;
import com.bookingtable.models.Role;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class PermissionDto {
	
	private Integer id;
	@NotEmpty
	@NotNull
	private String module;
	@NotEmpty
	@NotNull
	private String name;
	@NotNull
	private Collection<RoleDto> rolesDto = new ArrayList<>();
}
