package com.bookingtable.servicies.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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

}
