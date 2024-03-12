package com.bookingtable.servicies.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReservationDto;
import com.bookingtable.mappers.ReservationMapper;
import com.bookingtable.mappers.ReservationStatusMapper;
import com.bookingtable.models.Invoice;
import com.bookingtable.models.Reservation;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.repositories.ReservationStatusRepository;
import com.bookingtable.servicies.IReservationService;
@Service
public class ReservationService implements IReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReservationStatusRepository reservationStatusRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Override
	public List<ReservationDto> getAllReservation() {
		return reservationRepository.findAll()
				.stream()
				.map(i-> ReservationMapper.mapToDto(i))
				.collect(Collectors.toList());	
	}

	@Override
	public ReservationDto getReservationById(String id) {
		return ReservationMapper.mapToDto(reservationRepository.findById(id).get());
	}

	@Override
	public boolean createReservation(ReservationDto reservationDto) {
		try {
			reservationDto.setCreated(LocalDateTime.now());
			reservationDto.setReservationStatusDto(ReservationStatusMapper.mapToDto(reservationStatusRepository.findById(1).get()));
			if(reservationRepository.save(ReservationMapper.mapToModel(reservationDto)) != null){
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
	public boolean updateReservation(String id, Integer status, ReservationDto reservationDto) {
		try {
			var data = reservationRepository.findById(id).get();
			data.setReservationStatus(reservationStatusRepository.findById(status).get());
			if(reservationRepository.save(data) != null){
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
	public boolean deleteReservation(String id) {
		try {
			if(reservationRepository.findById(id)!=null) {
				reservationRepository.deleteById(id);
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
	public List<Reservation> getAllReservationForReceptionist(String emailReception) {
		List<Reservation> list = new ArrayList<>();
		var reservation = reservationRepository.findAll();
		var receptionist = receptionistRepository.findByEmail(emailReception);
		for(var i : reservation) {
			if(i.getDinnerTable().getRestaurant().getId().equals(receptionist.getRestaurant().getId())) {
				if(!i.getReservationStatus().getStatus().equalsIgnoreCase("Finished") && !i.getReservationStatus().getStatus().equalsIgnoreCase("Cancelled")) {
					list.add(i);
				}
			}
		}
		return list;
	}

	@Override
	public boolean changeReservationStatusConfirmed(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeReservationStatusFinnished(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
