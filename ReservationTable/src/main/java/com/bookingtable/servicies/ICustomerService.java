package com.bookingtable.servicies;

import com.bookingtable.dtos.CustomerDto;

import java.util.List;

public interface ICustomerService {
    public List<CustomerDto> getAllCustomer();

    public CustomerDto getCustomerById(String id);

    public boolean createCustomer(CustomerDto customer) ;
    public boolean updateCustomer(String id, CustomerDto customer);

    public boolean deleteCustomer(String id);
}
