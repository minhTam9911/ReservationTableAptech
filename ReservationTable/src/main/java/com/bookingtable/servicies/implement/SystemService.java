package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.helpers.GenerateCode;
import com.bookingtable.helpers.MailHelper;
import com.bookingtable.models.System;
import com.bookingtable.mappers.SystemMapper;
import com.bookingtable.repositories.GuestRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.IMailService;
import com.bookingtable.servicies.ISystemService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Service
public class SystemService implements ISystemService {

	@Autowired
	private SystemRepository systemRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private IMailService mailService;
	@Autowired
	private Environment environment;
	@Override
	public List<SystemDto> getAllSystems() {
		
		return systemRepository.findByRoleIdNot(1).stream().map(i->SystemMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public SystemDto getSystemsById(UUID id) {
		return SystemMapper.mapToDto(systemRepository.findById(id).get());
	}

	

	@Override
	public  ResultResponse<SystemDto> updateSystem(UUID id, SystemDto systemDto) {
		try {
			if(systemRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(receptionistRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(reservationAgentRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(guestRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(systemRepository.existEmail(systemDto.getEmail().toLowerCase(),id)!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			var data = systemRepository.findById(id).get();
			data.setFullname(systemDto.getFullname());
			data.setAddress(systemDto.getAddress());
			data.setDateOfBirth(systemDto.getDateOfBirth());
			data.setEmail(systemDto.getEmail());
			data.setPhoneNumber(systemDto.getPhoneNumber());
			data.setGender(systemDto.isGender());
			data.setUpdated(LocalDate.now());
			if(systemRepository.save(data)!=null) {
				return new  ResultResponse<SystemDto>(true, new SystemDto());
			}else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<SystemDto>(false, new SystemDto(e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<SystemDto> deleteSystem(UUID id) {
		try {
			
			if(systemRepository.findById(id)!=null) {
				systemRepository.deleteById(id);
				return new  ResultResponse<SystemDto>(true, new SystemDto());
			}else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<SystemDto>(false, new SystemDto(e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<SystemDto> insertSystem(SystemDto systemDto) {
		try {
			systemDto.setEmail(systemDto.getEmail().toLowerCase());
			systemDto.setCreated(LocalDate.now());
			systemDto.setUpdated(LocalDate.now());
			if(systemRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(receptionistRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(reservationAgentRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(guestRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			if(systemRepository.findByEmail(systemDto.getEmail().toLowerCase())!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
			var data = SystemMapper.mapToModel(systemDto);
			data.setPassword(GenerateCode.GeneratePassword(12));
			String email = environment.getProperty("spring.mail.username");
			String content = MailHelper.HtmlNewAccount(data.getFullname(), data.getEmail(), data.getPassword());
			if (mailService.send(email, data.getEmail(), "Account for you", content)) {
				
			} else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Send Email Fail"));
			}
			
			if(systemRepository.save(data)!=null) {
				return new  ResultResponse<SystemDto>(true, new SystemDto());
			}else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
		}
	}

	@Override
	public boolean changeStatus(UUID id) {
		try {
			var data = systemRepository.findById(id).get();
			if(data== null) {
				return false;
			}
			data.setStatus(!data.isStatus());
			if(systemRepository.save(data)!=null) return true;
			return false;
		}catch (Exception e) {
			return false;
		}
		
	}

}
