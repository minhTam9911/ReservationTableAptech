package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.SystemDto;
import com.bookingtable.models.System;
import com.bookingtable.mappers.SystemMapper;
import com.bookingtable.repositories.SystemRepository;
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
	@Override
	public List<SystemDto> getAllSystems() {
		
		return systemRepository.findByRoleIdNot(1).stream().map(i->SystemMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public SystemDto getSystemsById(UUID id) {
		return SystemMapper.mapToDto(systemRepository.findById(id).get());
	}

	

	@Override
	public boolean updateSystem(UUID id, SystemDto systemDto) {
		try {
			var data = systemRepository.findById(id).get();
			data.setFullname(systemDto.getFullname());
			data.setAddress(systemDto.getAddress());
			data.setDateOfBirth(systemDto.getDateOfBirth());
			data.setEmail(systemDto.getEmail());
			data.setPhoneNumber(systemDto.getPhoneNumber());
			data.setGender(systemDto.isGender());
			data.setUpdated(LocalDate.now());
			if(systemRepository.save(data)!=null) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteSystem(UUID id) {
		try {
			if(systemRepository.findById(id)!=null) {
				systemRepository.deleteById(id);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insertSystem(SystemDto systemDto) {
		try {
			systemDto.setCreated(LocalDate.now());
			systemDto.setUpdated(LocalDate.now());
			if(systemRepository.save(SystemMapper.mapToModel(systemDto))!=null) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
