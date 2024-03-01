package com.bookingtable.mappers;

import java.util.stream.Collectors;

import com.bookingtable.dtos.RoleDto;
import com.bookingtable.models.Role;

public class RoleMapper {

	public static Role mapToModel(RoleDto roleDto) {
		return Role.builder()
				.id(roleDto.getId())
				.name(roleDto.getName())
				
				.build();
	}
	public static RoleDto mapToDto(Role role) {
		return RoleDto.builder()
				.id(role.getId())
				.name(role.getName())
				
				.build();
	}
	
}
