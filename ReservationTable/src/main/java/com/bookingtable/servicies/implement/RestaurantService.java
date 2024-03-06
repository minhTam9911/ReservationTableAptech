package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.RestaurantMapper;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService implements IRestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Override
	public List<RestaurantDto> getAllRestaurants() {
		return restaurantRepository.findAll().stream().map(i->RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public RestaurantDto getRestaurantById(String id) {
		return RestaurantMapper.mapToDto(restaurantRepository.findById(id).get());
	}

	@Override
	public ResultResponse<RestaurantDto>  createRestaurant(RestaurantDto restaurantDto, String emailCreatedBy) {
		try {
			var check = reservationAgentRepository.findByEmail(emailCreatedBy);
			var check2 = restaurantRepository.findByReservationAgentEmail(emailCreatedBy);
			if(check2.size() >= check.getTotalRestaurant()) {
				return new ResultResponse<RestaurantDto>( false,new RestaurantDto());
			}
			var data = RestaurantMapper.mapToModel(restaurantDto);
			data.setReservationAgent(check);
			var saveChange = restaurantRepository.save(data);
			if(saveChange!=null) {
				return new ResultResponse<RestaurantDto>( true,new RestaurantDto(saveChange.getId(),true));
			}else {
				return new ResultResponse<RestaurantDto>( false,new RestaurantDto());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<RestaurantDto>( false,new RestaurantDto());
		}
	}

	@Override
	public boolean updateRestaurant(String id, RestaurantDto restaurantDto, String emailCreatedBy) {
		try {
			var data = restaurantRepository.findById(id).get();
			if(data==null || !data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
				return false;
			}
			data.setName(restaurantDto.getName());
			data.setAddress(restaurantDto.getAddress());
			data.setCity(restaurantDto.getCity());
			data.setDistrict(restaurantDto.getDistrict());
			data.setWard(restaurantDto.getWard());
			data.setCompanyMail(restaurantDto.getCompanyMail());
			data.setMainPhoneNumber(restaurantDto.getMainPhoneNumber());
			data.setFaxNumber(restaurantDto.getFaxNumber());
			data.setCreated(LocalDate.now());
			data.setUpdated(LocalDate.now());
			if(restaurantRepository.save(data)!=null) {
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
	public boolean deleteRestaurant(String id, String emailCreatedBy) {
		try {
			var data = restaurantRepository.findById(id).get();
			if(data!=null && data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
				restaurantRepository.deleteById(id);
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
	public List<RestaurantDto> getAllRestaurantsForAgent(String idAgent) {
		return restaurantRepository.findByReservationAgentEmail(idAgent).stream().map(i->RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public RestaurantDto getRestaurantById(String id, String idAgent) {
		try {
			var data = restaurantRepository.findById(id).get();
			if(data!=null && data.getReservationAgent().getEmail().equalsIgnoreCase(idAgent)) {
				return RestaurantMapper.mapToDto(data);
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
   
}
