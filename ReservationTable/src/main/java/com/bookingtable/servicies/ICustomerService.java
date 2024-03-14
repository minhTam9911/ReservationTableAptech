package com.bookingtable.servicies;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;

import java.util.List;

public interface ICustomerService {
    public List<CustomerDto> getAllCustomer();

    public CustomerDto getCustomerById(String id);

    public ResultResponse<String> createCustomer(CustomerDto customer) ;
    public ResultResponse<String> updateCustomer(String id, CustomerDto customer);

    public ResultResponse<String> deleteCustomer(String id);
    public boolean changeStatus(String email, String code);

    public CustomerDto getCustomerByEmail(String email) ;

    }
