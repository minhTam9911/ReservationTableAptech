package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.mappers.CustomerMapper;
import com.bookingtable.models.Customer;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.servicies.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository guestRepository;

    @Override
    public List<CustomerDto> getAllGuest() {
        return guestRepository.findAll().stream()
                .map(CustomerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getGuestById(String id) {
        Customer customer = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));
        return CustomerMapper.mapToDto(customer);
    }

    @Override
    public boolean createGuest(CustomerDto customer) {
        Customer cus = CustomerMapper.mapToModel(customer);
        Customer savedCustomer = guestRepository.save(cus);
        return CustomerMapper.mapToDto(savedCustomer)!=null;
    }

    @Override
    public boolean updateGuest(String id, CustomerDto customer) {
        Customer existingGuest = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

        Customer updatedGuest = CustomerMapper.mapToModel(customer);
        updatedGuest.setId(existingGuest.getId()); // Ensure the ID remains the same

        Customer savedGuest = guestRepository.save(updatedGuest);
        return CustomerMapper.mapToDto(savedGuest)!=null;
    }

    @Override
    public boolean deleteGuest(String id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
