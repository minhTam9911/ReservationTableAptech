package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ReservationAgentDto;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomerService implements ICustomerService {

	@Autowired
	private SystemRepository systemRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private IMailService mailService;
	@Autowired
	private Environment environment;
	@Autowired
	private BCryptPasswordEncoder cryptPasswordEncoder;
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
        	customerDto.setEmail(customerDto.getEmail().toLowerCase());
            if(customerRepository.existEmail(customerDto.getEmail().toLowerCase(),id)!=null) {
            return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
            }
            
			if(receptionistRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
			}
			if(reservationAgentRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
			}
			if(systemRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
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
            
			if(receptionistRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
			}
			if(reservationAgentRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
			}
			if(systemRepository.findByEmail(customerDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<CustomerDto>(false, new CustomerDto("Email already"));
			}

            var data = CustomerMapper.mapToModel(customerDto);
            var hashPassword = cryptPasswordEncoder.encode(customerDto.getPassword());
            data.setPassword(hashPassword);
            data.setSecurityCode(GenerateCode.RandomSecurityCode());
            String email = environment.getProperty("spring.mail.username");
            var content = "Click here to activate: "+"http://localhost:8080/verify?email="+data.getEmail()+"&securityCode="+ data.getSecurityCode();
		System.out.println(content);
            if (mailService.send(email, data.getEmail(), "Active Account", content)) {

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

	@Override
	public boolean changeStatus(String email, String code) {
		var check = customerRepository.findByEmail(email);
		if(check!=null && !check.isStatus()) {
			if(check.getSecurityCode().equals(code)) {
				check.setStatus(true);
				check.setSecurityCode(null);
				if(customerRepository.save(check) !=null) {
					return true;
				}
			}
		}
		return false;
	}
    @Override
    public CustomerDto getCustomerByEmail(String email) {
        try {
            // Convert email to lowercase for case-insensitive comparison
            email = email.toLowerCase();

            // Find the customer by email
            Customer customer = customerRepository.findByEmail(email);

            // If customer is found, map it to DTO and return
            if (customer != null) {
                return CustomerMapper.mapToDto(customer);
            } else {
                // If customer is not found, return null or throw an exception as per your requirement
                return null; // Or throw new Exception("Customer not found for email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or handle the exception as per your requirement
        }
    }


}
