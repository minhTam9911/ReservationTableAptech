package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.servicies.IReceptionistService;
@Service
public class ReceptionistService implements IReceptionistService {

	@Override
	public List<ReceptionistDto> getAllReceptionistts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReceptionistDto getReceptionistById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createReceptionist(ReceptionistDto restaurantDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReceptionist(String id, ReceptionistDto receptionistDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReceptionist(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
