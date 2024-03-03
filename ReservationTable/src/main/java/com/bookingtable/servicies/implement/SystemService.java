package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bookingtable.dtos.DinnerTableTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.mappers.SystemMapper;
import com.bookingtable.repositories.GuestRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.ISystemService;

@Service
public class SystemService implements ISystemService {

	@Autowired
	private SystemRepository systemRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private SystemRepository repository;
	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
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
			var data = systemRepository.findById(id).get();
			data.setFullname(systemDto.getFullname());
			data.setAddress(systemDto.getAddress());
			data.setDateOfBirth(systemDto.getDateOfBirth());
			data.setEmail(systemDto.getEmail());
			data.setPhoneNumber(systemDto.getPhoneNumber());
			data.setGender(systemDto.isGender());
			data.setUpdated(LocalDate.now());
			if(systemRepository.save(data)!=null) {
                return new ResultResponse<SystemDto>(true, new SystemDto());
            } else {
                return new ResultResponse<SystemDto>(false, new SystemDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<SystemDto>(false, new SystemDto());
        }
	}

	@Override
	public  ResultResponse<SystemDto> deleteSystem(UUID id) {
		try {

			if(systemRepository.findById(id)!=null) {
				systemRepository.deleteById(id);
                return new ResultResponse<SystemDto>(true, new SystemDto());
            } else {
                return new ResultResponse<SystemDto>(false, new SystemDto());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultResponse<SystemDto>(false, new SystemDto());
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
			if(systemRepository.save(SystemMapper.mapToModel(systemDto))!=null) {
				return new  ResultResponse<SystemDto>(false, new SystemDto());
			}else {
				return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<SystemDto>(false, new SystemDto("Email already"));
		}
	}

}
