package com.bookingtable.servicies.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReservationStatusDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
//import com.bookingtable.mappers.PermissionMapper;
import com.bookingtable.mappers.ReservationStatusMapper;
import com.bookingtable.mappers.RoleMapper;
import com.bookingtable.repositories.ReservationStatusRepository;
import com.bookingtable.repositories.RoleRepository;
import com.bookingtable.servicies.IReservationStatusService;

@Service
public class ReservationStatusService implements IReservationStatusService {

	@Autowired
	private ReservationStatusRepository reservationStatusRepository;
	@Override
	public List<ReservationStatusDto> getAllReservationStatuses() {
		
		return reservationStatusRepository.findAll().stream().map(i->ReservationStatusMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ReservationStatusDto getReservationStatusById(Integer id) {
		return ReservationStatusMapper.mapToDto(reservationStatusRepository.findById(id).get());

	}

	@Override
	public ResultResponse<ReservationStatusDto>  createReservationStatus(ReservationStatusDto reservationStatusDto) {
		try {
			if(reservationStatusRepository.findByStatusIgnoreCase(reservationStatusDto.getStatus()).size()>0) {
				return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,"Status already"));
			}
			if(reservationStatusRepository.save(ReservationStatusMapper.mapToModel(reservationStatusDto))!=null) {
				return new ResultResponse<ReservationStatusDto>(true,new ReservationStatusDto());
			}else {
				return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,"Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<ReservationStatusDto> updateReservationStatus(Integer id, ReservationStatusDto reservationStatusDto) {
		try {
			if(reservationStatusRepository.existStatus(reservationStatusDto.getStatus(),id).size()>0) {
				return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,"Status already"));
			}
			var data = reservationStatusRepository.findById(id).get();
			data.setStatus(reservationStatusDto.getStatus());
			data.setReason(reservationStatusDto.getReason());
			data.setDescription(reservationStatusDto.getDescription());
			if(reservationStatusRepository.save(data)!=null) {
				return new ResultResponse<ReservationStatusDto>(true,new ReservationStatusDto());
			}else {
				return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,"Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,e.getMessage()));
		}
	}

	@Override
	public  ResultResponse<ReservationStatusDto> deleteReservationStatus(Integer id) {
		try {
			if(reservationStatusRepository.findById(id)!=null) {
				reservationStatusRepository.deleteById(id);
				return new ResultResponse<ReservationStatusDto>(true,new ReservationStatusDto());
			}else {
				return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,"Failure"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<ReservationStatusDto>(false,new ReservationStatusDto(0,e.getMessage()));
		}
	}
}
