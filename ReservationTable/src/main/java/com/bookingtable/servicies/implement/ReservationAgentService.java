package com.bookingtable.servicies.implement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.ReservationAgentMapper;
import com.bookingtable.repositories.GuestRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.SystemRepository;
import com.bookingtable.servicies.IReservationAgentService;
@Service
public class ReservationAgentService implements IReservationAgentService {

	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private SystemRepository repository;
	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Override
	public List<ReservationAgentDto> getAllReservationAgents() {
		
		return reservationAgentRepository.findAll().stream().map(i->ReservationAgentMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ReservationAgentDto getReservationAgentById(UUID id) {
		return ReservationAgentMapper.mapToDto(reservationAgentRepository.findById(id).get());
	}

	

	@Override
	public ResultResponse<ReservationAgentDto> updateReservationAgent(UUID id, ReservationAgentDto reservationAgentDto) {
		try {
			var data = reservationAgentRepository.findById(id).get();
			data.setFullName(reservationAgentDto.getFullName());
			data.setAddress(reservationAgentDto.getAddress());
			data.setCity(reservationAgentDto.getCity());
			data.setDistrict(reservationAgentDto.getDistrict());
			data.setWard(reservationAgentDto.getWard());
			data.setEmail(reservationAgentDto.getEmail());
			data.setHomePhoneNumber(reservationAgentDto.getHomePhoneNumber());
			data.setCellularPhoneNumber(reservationAgentDto.getCellularPhoneNumber());
			data.setUpdated(LocalDate.now());
			if(reservationAgentRepository.save(data)!=null) {
				return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}else {
				return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
		}
	}

	@Override
	public ResultResponse<ReservationAgentDto> deleteReservationAgent(UUID id) {
		try {
			if(reservationAgentRepository.findById(id)!=null) {
				reservationAgentRepository.deleteById(id);
				return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}else {
				return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
		}
	}

	@Override
	public ResultResponse<ReservationAgentDto> createReservationAgent(ReservationAgentDto reservationAgentDto) {
		try {
			reservationAgentDto.setEmail(reservationAgentDto.getEmail().toLowerCase());
			reservationAgentDto.setCreated(LocalDate.now());
			reservationAgentDto.setUpdated(LocalDate.now());
			reservationAgentDto.setCreateBy(null);
			
			if(reservationAgentRepository.save(ReservationAgentMapper.mapToModel(reservationAgentDto))!=null) {
				return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}else {
				return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<ReservationAgentDto>(true, new ReservationAgentDto());
		}
	}

}
