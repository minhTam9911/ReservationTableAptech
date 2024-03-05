package com.bookingtable.servicies;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;

import java.util.List;

public interface ICustomerService {
    public List<CustomerDto> getAllCustomer();

    public CustomerDto getCustomerById(String id);

    public ResultResponse<CustomerDto> createCustomer(CustomerDto customer) ;
    public ResultResponse<CustomerDto> updateCustomer(String id, CustomerDto customer);

    public ResultResponse<CustomerDto> deleteCustomer(String id);
}
