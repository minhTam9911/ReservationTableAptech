package com.bookingtable.mappers;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.models.Customer;

public class CustomerMapper {
    public static Customer mapToModel(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .fullName(customerDto.getFullname())
                .phoneNumber(customerDto.getPhoneNumber())
                .address(customerDto.getAddress())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .gender(customerDto.isGender())
                .created(customerDto.getCreated())
                .updated(customerDto.getUpdated())
                .dateOfBirth(customerDto.getDateOfBirth())
                .role(RoleMapper.mapToModel(customerDto.getRoleDto()))
                .build();
    }

    public static CustomerDto mapToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .fullname(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .gender(customer.isGender())
                .created(customer.getCreated())
                .updated(customer.getUpdated())
                .dateOfBirth(customer.getDateOfBirth())
                .roleDto(RoleMapper.mapToDto(customer.getRole()))
                .build();
    }

}
