package com.bookingtable.servicies.implement;

import java.util.ArrayList;
import java.util.List;

import com.bookingtable.dtos.SystemDto;
import com.bookingtable.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.helpers.MailHelper;
import com.bookingtable.models.Customer;
import com.bookingtable.models.Receptionist;
import com.bookingtable.models.ReservationAgent;
import com.bookingtable.models.Role;
import com.bookingtable.models.System;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.IAccountService;
import com.bookingtable.servicies.IMailService;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private SystemRepository systemRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired 
	private IMailService iMailService;
	@Autowired
	private Environment environment;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = username.toLowerCase();
		System system = systemRepository.findByEmail(username);
		if(system !=null) {
			if (system.isStatus()) {
				
				List<GrantedAuthority> roles = new ArrayList<>();
					roles.add(new SimpleGrantedAuthority(system.getRole().getName()));
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				return new User(username, system.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
						accountNonLocked, roles);
			
			} else {
				throw new UsernameNotFoundException("User No Active");
			}
		}
		
		ReservationAgent reservationAgent = reservationAgentRepository.findByEmail(username);
		if(reservationAgent !=null) {
			if (reservationAgent.isStatus()) {
				
				List<GrantedAuthority> roles = new ArrayList<>();
					roles.add(new SimpleGrantedAuthority(reservationAgent.getRole().getName()));
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				return new User(username, reservationAgent.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
						accountNonLocked, roles);
			
			} else {
				throw new UsernameNotFoundException("User No Active");
			}
		}
		Receptionist receptionist = receptionistRepository.findByEmail(username);
		if(receptionist !=null) {
			if (receptionist.isStatus()) {
				
				List<GrantedAuthority> roles = new ArrayList<>();
					roles.add(new SimpleGrantedAuthority(receptionist.getRole().getName()));
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				return new User(username, receptionist.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
						accountNonLocked, roles);
			
			} else {
				throw new UsernameNotFoundException("User No Active");
			}
		}
		Customer customer = customerRepository.findByEmail(username);
		if(customer !=null) {
			if (customer.isStatus()) {
				
				List<GrantedAuthority> roles = new ArrayList<>();
					roles.add(new SimpleGrantedAuthority(customer.getRole().getName()));
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				return new User(username, customer.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
						accountNonLocked, roles);
			
			} else {
				throw new UsernameNotFoundException("User No Active");
			}
		}
		else {
			throw new UsernameNotFoundException("User not found");
		}
	}

	@Override
	public ResultResponse<String> forgotPassword(String email) {
		if(systemRepository.findByEmail(email)!=null) {
			var data = systemRepository.findByEmail(email);
			var code = GenerateCode.RandomSecurityCode();
			data.setSecurityCode(code);
			systemRepository.save(data);
			 String emailFrom = environment.getProperty("spring.mail.username");
            var content =MailHelper.HtmlVerify(code);
            iMailService.send(emailFrom, data.getEmail(), "Verification code", content);
			return new ResultResponse<String>(true, "Please check your email again");
		}
		if(reservationAgentRepository.findByEmail(email)!=null) {
			var data = reservationAgentRepository.findByEmail(email);
			var code = GenerateCode.RandomSecurityCode();
			data.setSecurityCode(code);
			reservationAgentRepository.save(data);
			 String emailFrom = environment.getProperty("spring.mail.username");
            var content =MailHelper.HtmlVerify(code);
            iMailService.send(emailFrom, data.getEmail(), "Verification code", content);
			return new ResultResponse<String>(true, "Please check your email again");
		}
		if(receptionistRepository.findByEmail(email)!=null) {
			var data = receptionistRepository.findByEmail(email);
			var code = GenerateCode.RandomSecurityCode();
			data.setSecurityCode(code);
			receptionistRepository.save(data);
			 String emailFrom = environment.getProperty("spring.mail.username");
            var content =MailHelper.HtmlVerify(code);
            iMailService.send(emailFrom, data.getEmail(), "Verification code", content);
			return new ResultResponse<String>(true, "Please check your email again");
		}
		if(customerRepository.findByEmail(email)!=null) {
			var data = customerRepository.findByEmail(email);
			var code = GenerateCode.RandomSecurityCode();
			data.setSecurityCode(code);
			customerRepository.save(data);
			 String emailFrom = environment.getProperty("spring.mail.username");
            var content =MailHelper.HtmlVerify(code);
            iMailService.send(emailFrom, data.getEmail(), "Verification code", content);
			return new ResultResponse<String>(true, "Please check your email again");
		}
		return new ResultResponse<String>(false, "Email does not exist");
	}

	@Override
	public ResultResponse<String> verifyCode(String email, String code) {
		if(systemRepository.findByEmail(email)!=null) {
			var data = systemRepository.findByEmail(email);
			if(data.getSecurityCode().equals(code)) {
				data.setSecurityCode(null);
				systemRepository.save(data);
				return new ResultResponse<String>(true, "Verify Code is valid");
			}
		}
		if(reservationAgentRepository.findByEmail(email)!=null) {
			var data = reservationAgentRepository.findByEmail(email);
			if(data.getSecurityCode().equals(code)) {
				data.setSecurityCode(null);
				reservationAgentRepository.save(data);
				return new ResultResponse<String>(true, "Verify Code is valid");
			}
		}
		if(receptionistRepository.findByEmail(email)!=null) {
			var data = receptionistRepository.findByEmail(email);
			if(data.getSecurityCode().equals(code)) {
				data.setSecurityCode(null);
				receptionistRepository.save(data);
				return new ResultResponse<String>(true, "Verify Code is valid");
			}
		}
		if(customerRepository.findByEmail(email)!=null) {
			var data = customerRepository.findByEmail(email);
			if(data.getSecurityCode().equals(code)) {
				data.setSecurityCode(null);
				customerRepository.save(data);
				return new ResultResponse<String>(true, "Verify Code is valid");
			}
		}
		return  new ResultResponse<String>(false, "Security code not match");
	}

	@Override
	public ResultResponse<String> saveResetPassword(String email, String password) {
		if(systemRepository.findByEmail(email)!=null) {
			var data = systemRepository.findByEmail(email);
			
				data.setPassword(password);
				systemRepository.save(data);
				return new ResultResponse<String>(true, "Reset Password Successful");
			
		}
		if(reservationAgentRepository.findByEmail(email)!=null) {
			var data = reservationAgentRepository.findByEmail(email);
			data.setPassword(password);
				reservationAgentRepository.save(data);
				return new ResultResponse<String>(true, "Reset Password Successful");
		}
		if(receptionistRepository.findByEmail(email)!=null) {
			var data = receptionistRepository.findByEmail(email);
			data.setPassword(password);
				receptionistRepository.save(data);
				return new ResultResponse<String>(true, "Reset Password Successful");
		}
		if(customerRepository.findByEmail(email)!=null) {
			var data = customerRepository.findByEmail(email);
			data.setPassword(password);
				customerRepository.save(data);
				return new ResultResponse<String>(true, "Reset Password Successful");
		}
		return  new ResultResponse<String>(false, "Reset Password Failure");
	}

	public ResultResponse<String> updateProfile(String email, SystemDto systemDto) {
		// Common logic for updating the user profile
		if(systemRepository.findByEmail(email)!=null) {
			var data = systemRepository.findByEmail(email);
			data.setFullname(systemDto.getFullname());

			data.setPassword(systemDto.getPassword());
			systemRepository.save(data);
			return new ResultResponse<String>(true, "Reset Password Successful");

		}
		if(reservationAgentRepository.findByEmail(email)!=null) {
			var data = reservationAgentRepository.findByEmail(email);
			data.setFullName(systemDto.getFullname());
			data.setPassword(systemDto.getPassword());
			reservationAgentRepository.save(data);
			return new ResultResponse<String>(true, "Reset Password Successful");
		}
		if(receptionistRepository.findByEmail(email)!=null) {
			var data = receptionistRepository.findByEmail(email);
			data.setFullname(systemDto.getFullname());

			data.setPassword(systemDto.getPassword());
			receptionistRepository.save(data);
			return new ResultResponse<String>(true, "Reset Password Successful");
		}
		if(customerRepository.findByEmail(email)!=null) {
			var data = customerRepository.findByEmail(email);
			data.setFullName(systemDto.getFullname());
			data.setPassword(systemDto.getPassword());
			customerRepository.save(data);
			return new ResultResponse<String>(true, "Reset Password Successful");
		}

		return new ResultResponse<String>(true, "Profile updated successfully");
	}
	public SystemDto getUserByEmail(String email) {
		System system = systemRepository.findByEmail(email);
		if (system != null) {
			return SystemMapper.mapToDto(system);
		}

		ReservationAgent reservationAgent = reservationAgentRepository.findByEmail(email);
		if (reservationAgent != null) {
			SystemDto systemDto = new SystemDto();
			systemDto.setId(reservationAgent.getId());
			systemDto.setFullname(reservationAgent.getFullName());
			systemDto.setAddress(reservationAgent.getAddress());
			systemDto.setEmail(reservationAgent.getEmail());
			systemDto.setPassword(reservationAgent.getPassword());
			systemDto.setStatus(reservationAgent.isStatus());
			systemDto.setCreated(reservationAgent.getCreated());
			systemDto.setUpdated(reservationAgent.getUpdated());
			systemDto.setRoleDto(RoleMapper.mapToDto(reservationAgent.getRole())); // Assuming you have a method to map Role entity to RoleDto
			return systemDto;
		}

//		Receptionist receptionist = receptionistRepository.findByEmail(email);
//		if (receptionist != null) {
//			return ReceptionistMapper.mapToDto(receptionist);
//		}
//
//		Customer customer = customerRepository.findByEmail(email);
//		if (customer != null) {
//			return mapCustomerToSystemDto(customer);
//		}

		return null;
	}

}
