package com.bookingtable.mappers;

import java.util.stream.Collectors;

import com.bookingtable.dtos.PermissionDto;
import com.bookingtable.models.Permission;

public class PermissionMapper {
	public static Permission mapToModel(PermissionDto permissionDto) {
		return Permission.builder()
				.id(permissionDto.getId())
				.module(permissionDto.getModule())
				.name(permissionDto.getName())
				.roles(permissionDto.getRolesDto()
						.stream().map(i->RoleMapper.mapToModel(i))
						.collect(Collectors.toList()))
				.build();
	}
	public static PermissionDto mapToDto(Permission permission) {
		return PermissionDto.builder()
				.id(permission.getId())
				.module(permission.getModule())
				.name(permission.getName())
				.rolesDto(permission.getRoles()
						.stream().map(i->RoleMapper.mapToDto(i))
						.collect(Collectors.toList()))
				.build();
	}
}
