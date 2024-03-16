package com.bookingtable.mappers;

import com.bookingtable.dtos.SystemDto;
import com.bookingtable.models.System;

public class SystemMapper {
	public static  System mapToModel(SystemDto systemDto) {
		return System.builder()
				.id(systemDto.getId())
				.fullname(systemDto.getFullname())
				.phoneNumber(systemDto.getPhoneNumber())
				.address(systemDto.getAddress())
				.email(systemDto.getEmail())
				.password(systemDto.getPassword())
				.gender(systemDto.isGender())
				.created(systemDto.getCreated())
				.image(systemDto.getImage())
				.updated(systemDto.getUpdated())
				.dateOfBirth(systemDto.getDateOfBirth())
				.role(RoleMapper.mapToModel(systemDto.getRoleDto()))
				.build();
	}
	
	public static  SystemDto mapToDto(System system) {
		return SystemDto.builder()
				.id(system.getId())
				.fullname(system.getFullname())
				.phoneNumber(system.getPhoneNumber())
				.address(system.getAddress())
				.email(system.getEmail())
				.password(system.getPassword())
				.gender(system.isGender())
				.image(system.getImage())
				.created(system.getCreated())
				.updated(system.getUpdated())
				.status(system.isStatus())
				.detailAddress(system.getAddress())
				.dateOfBirth(system.getDateOfBirth())
				.roleDto(RoleMapper.mapToDto(system.getRole()))
				.build();
	}
	
}
