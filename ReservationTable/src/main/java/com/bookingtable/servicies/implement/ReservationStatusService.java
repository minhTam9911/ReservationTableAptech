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
	public ResultResponse<String>  createReservationStatus(ReservationStatusDto reservationStatusDto) {
		try {
			if(reservationStatusRepository.findByStatusIgnoreCase(reservationStatusDto.getStatus()).size()>0) {
				return new  ResultResponse<String>(true,2, "Status already");
			}
			if(reservationStatusRepository.save(ReservationStatusMapper.mapToModel(reservationStatusDto))!=null) {
				return new  ResultResponse<String>(true,1, "Process Successfully");
			}else {
				return new  ResultResponse<String>(true,2, "Process Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<String>(true,2, e.getMessage());
		}
	}

	@Override
	public  ResultResponse<String> updateReservationStatus(Integer id, ReservationStatusDto reservationStatusDto) {
		try {
			if(reservationStatusRepository.existStatus(reservationStatusDto.getStatus(),id).size()>0) {
				return new  ResultResponse<String>(true,2, "Status already");
			}
			var data = reservationStatusRepository.findById(id).get();
			data.setStatus(reservationStatusDto.getStatus());
			data.setReason(reservationStatusDto.getReason());
			data.setDescription(reservationStatusDto.getDescription());
			if(reservationStatusRepository.save(data)!=null) {
				return new  ResultResponse<String>(true,1, "Process Successfully");
			}else {
				return new  ResultResponse<String>(true,2, "Process Failure");			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<String>(true,2, e.getMessage());		}
	}

	@Override
	public  ResultResponse<String> deleteReservationStatus(Integer id) {
		try {
			if(reservationStatusRepository.findById(id)!=null) {
				reservationStatusRepository.deleteById(id);
				return new  ResultResponse<String>(true,1, "Process Successfully");			
				}else {
					return new  ResultResponse<String>(true,2, "Process Failure");			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResultResponse<String>(true,2, e.getMessage());		}
	}
}
