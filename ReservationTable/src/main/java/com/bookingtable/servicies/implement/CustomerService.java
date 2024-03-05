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
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDto> getAllCustomer() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));
        return CustomerMapper.mapToDto(customer);
    }

    @Override
    public boolean createCustomer(CustomerDto customer) {
        Customer cus = CustomerMapper.mapToModel(customer);
        Customer savedCustomer = customerRepository.save(cus);
        return CustomerMapper.mapToDto(savedCustomer)!=null;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDto customer) {
        Customer existingGuest = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

        Customer updatedGuest = CustomerMapper.mapToModel(customer);
        updatedGuest.setId(existingGuest.getId()); // Ensure the ID remains the same

        Customer savedGuest = customerRepository.save(updatedGuest);
        return CustomerMapper.mapToDto(savedGuest)!=null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
