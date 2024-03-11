package com.bookingtable.servicies.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
	@Override
	public ResultResponse<String> updateProfile(SystemDto updatedProfile,String email) {
		System system = systemRepository.findByEmail(email);
		if (system != null) {
			system.setFullname(updatedProfile.getFullname());
			system.setAddress(updatedProfile.getAddress());
			system.setPhoneNumber(updatedProfile.getPhoneNumber());
			system.setPassword(updatedProfile.getPassword());
			systemRepository.save(system);
			return new ResultResponse<String>(true, "Profile updated successfully");
		}

		ReservationAgent reservationAgent = reservationAgentRepository.findByEmail(email);
		if (reservationAgent != null) {
			system.setFullname(reservationAgent.getFullName());
			system.setAddress(reservationAgent.getAddress());
			system.setPhoneNumber(reservationAgent.getCellularPhoneNumber());
			system.setPassword(reservationAgent.getPassword());
			reservationAgentRepository.save(reservationAgent);
			return new ResultResponse<String>(true, "Profile updated successfully");
		}

		Receptionist receptionist = receptionistRepository.findByEmail(email);
		if (reservationAgent != null) {
			system.setFullname(receptionist.getFullname());
			system.setAddress(receptionist.getAddress());
			system.setPhoneNumber(receptionist.getPhoneNumber());
			system.setPassword(receptionist.getPassword());
			system.setDateOfBirth(receptionist.getDateOfBirth());
			receptionistRepository.save(receptionist);
			return new ResultResponse<String>(true, "Profile updated successfully");
		}
		return new ResultResponse<String>(false, "User not found");
	}
	@Override
	public SystemDto findByEmail(String email) {
		System system = systemRepository.findByEmail(email);
		if (system != null) {
			system.setFullname(system.getFullname());
			system.setAddress(system.getAddress());
			system.setPhoneNumber(system.getPhoneNumber());
			system.setPassword(system.getPassword());
			return SystemMapper.mapToDto(system);
		}

		ReservationAgent reservationAgent = reservationAgentRepository.findByEmail(email);
		if (reservationAgent != null) {
			system.setFullname(reservationAgent.getFullName());
			system.setAddress(reservationAgent.getAddress());
			system.setPhoneNumber(reservationAgent.getCellularPhoneNumber());
			system.setPassword(reservationAgent.getPassword());
			return SystemMapper.mapToDto(system);
		}

		Receptionist receptionist = receptionistRepository.findByEmail(email);
		if (receptionist != null) {
			system.setFullname(receptionist.getFullname());
			system.setAddress(receptionist.getAddress());
			system.setPhoneNumber(receptionist.getPhoneNumber());
			system.setPassword(receptionist.getPassword());
			system.setDateOfBirth(receptionist.getDateOfBirth());
			return SystemMapper.mapToDto(system);
		}

		return SystemMapper.mapToDto(system);
	}



}
