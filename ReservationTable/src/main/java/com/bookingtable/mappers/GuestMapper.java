package com.bookingtable.mappers;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.GuestDto;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.Guest;

public class GuestMapper {
    public static Guest mapToModel(GuestDto guestDto) {
        return Guest.builder()
                .id(guestDto.getId())
                .fullname(guestDto.getFullname())
                .address(guestDto.getAddress())
                .email(guestDto.getEmail())
                .created(guestDto.getCreated())
                .phoneNumber(guestDto.getPhoneNumber())
                .dateOfBirth(guestDto.getDateOfBirth())
                .updated(guestDto.getUpdated())
                .password(guestDto.getPassword())
                .gender(guestDto.isGender())
                .role(RoleMapper.mapToModel(guestDto.getRoleDto()))
                .build();
    }

    public static GuestDto mapToDto(Guest guest) {
        return GuestDto.builder()
                .id(guest.getId())
                .fullname(guest.getFullname())
                .address(guest.getAddress())
                .email(guest.getEmail())
                .created(guest.getCreated())
                .phoneNumber(guest.getPhoneNumber())
                .dateOfBirth(guest.getDateOfBirth())
                .updated(guest.getUpdated())
                .password(guest.getPassword())
                .gender(guest.isGender())
                .roleDto(RoleMapper.mapToDto(guest.getRole()))
                .build();
    }
}
