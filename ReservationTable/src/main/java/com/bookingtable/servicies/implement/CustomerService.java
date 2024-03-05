package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.mappers.CustomerMapper;
import com.bookingtable.mappers.SystemMapper;
import com.bookingtable.models.Customer;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.ICustomerService;
import com.bookingtable.servicies.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private IMailService mailService;
    @Autowired
    private Environment environment;
    @Override
    public List<CustomerDto> getAllCustomer() {

        return customerRepository.findAll().stream() .map(CustomerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(String id) {
        return CustomerMapper.mapToDto(customerRepository.findById(id).get());
    }



    @Override
    public ResultResponse<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
        try {

            if(customerRepository.existEmail(customerDto.getEmail().toLowerCase(),id)!=null) {
            return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
            }

            var data = customerRepository.findById(id).get();
            data.setFullName(customerDto.getFullname());
            data.setAddress(customerDto.getAddress());
            data.setDateOfBirth(customerDto.getDateOfBirth());
            data.setEmail(customerDto.getEmail());
            data.setPhoneNumber(customerDto.getPhoneNumber());
            data.setGender(customerDto.isGender());
            data.setUpdated(LocalDate.now());
            if(customerRepository.save(data)!=null) {
                return new  ResultResponse<CustomerDto>(true, new CustomerDto());
            }else {
                return new  ResultResponse<CustomerDto>(false, new CustomerDto("Failure"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new  ResultResponse<CustomerDto>(false, new CustomerDto(e.getMessage()));
        }
    }

    @Override
    public  ResultResponse<CustomerDto> deleteCustomer(String id) {
        try {

            if(customerRepository.findById(id)!=null) {
                customerRepository.deleteById(id);
                return new ResultResponse<CustomerDto>(true, new CustomerDto());
            } else {
                return new ResultResponse<CustomerDto>(false, new CustomerDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<CustomerDto>(false, new CustomerDto(e.getMessage()));
        }
    }

    @Override
    public  ResultResponse<CustomerDto> createCustomer(CustomerDto customerDto) {
        try {
            customerDto.setEmail(customerDto.getEmail().toLowerCase());
            if(customerRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
                return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
            }

            var data = CustomerMapper.mapToModel(customerDto);
            data.setPassword(GenerateCode.GeneratePassword(12));
            String email = environment.getProperty("spring.mail.username");
//			String content = MailHelper.HtmlNewAccount(data.getFullname(), data.getEmail(), data.getPassword());
            if (mailService.send(email, data.getEmail(), "Account for you", "")) {

            } else {
                return new  ResultResponse<CustomerDto>(false, new CustomerDto("Send Email Fail"));
            }

            if(customerRepository.save(data)!=null) {
                return new  ResultResponse<CustomerDto>(true, new CustomerDto());
            }else {
                return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
        }
    }


}
