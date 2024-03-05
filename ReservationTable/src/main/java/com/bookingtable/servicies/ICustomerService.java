package com.bookingtable.servicies;

import com.bookingtable.dtos.CustomerDto;

import java.util.List;

public interface ICustomerService {
    public List<CustomerDto> getAllGuest();

    public CustomerDto getGuestById(String id);

    public boolean createGuest(CustomerDto customer) ;
    public boolean updateGuest(String id, CustomerDto customer);

    public boolean deleteGuest(String id);
}
