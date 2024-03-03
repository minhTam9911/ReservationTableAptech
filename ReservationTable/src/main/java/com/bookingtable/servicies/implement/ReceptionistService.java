package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.mappers.ReceptionistMapper;
import com.bookingtable.mappers.ReservationAgentMapper;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.servicies.IReceptionistService;
@Service
public class ReceptionistService implements IReceptionistService {

	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Override
	public List<ReceptionistDto> getAllReceptionist() {
		
		return receptionistRepository.findAll().stream().map(i->ReceptionistMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public  ReceptionistDto getReceptionistById(String id) {
		return ReceptionistMapper.mapToDto(receptionistRepository.findById(id).get());
	}

	

	@Override
	public boolean updateReceptionist(String id, ReceptionistDto receptionistDto) {
		try {
			var data = receptionistRepository.findById(id).get();
			data.setFullname(receptionistDto.getFullname());
			data.setAddress(receptionistDto.getAddress());
			data.setEmail(receptionistDto.getEmail());
			data.setPhoneNumber(receptionistDto.getPhoneNumber());
			data.setDateOfBirth(receptionistDto.getDateOfBirth());
			data.setGender(receptionistDto.isGender());
			data.setUpdated(LocalDate.now());
			if(receptionistRepository.save(data)!=null) {
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
	public boolean deleteReceptionist(String id) {
		try {
			if(receptionistRepository.findById(id)!=null) {
				receptionistRepository.deleteById(id);
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
	public boolean createReceptionist(ReceptionistDto receptionistDto) {
		try {
			receptionistDto.setCreated(LocalDate.now());
			receptionistDto.setUpdated(LocalDate.now());
			if(receptionistRepository.save(ReceptionistMapper.mapToModel(receptionistDto))!=null) {
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
