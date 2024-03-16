package com.bookingtable.servicies;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ICustomerService {
    public List<CustomerDto> getAllCustomer();

    public CustomerDto getCustomerById(String id);

    public ResultResponse<String> createCustomer(CustomerDto customer) ;
    public ResultResponse<String> updateCustomer(String fullname, String address, String phoneNumber, LocalDate dateOfBirth, String email);
    public ResultResponse<String> uploadImage(MultipartFile  file, String email) ;
    public ResultResponse<String> deleteCustomer(String id);
    public boolean changeStatus(String email, String code);

    public CustomerDto getCustomerByEmail(String email) ;

    }
