package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.mappers.RestaurantMapper;
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
	@Override
	public List<RestaurantDto> getAllRestaurants() {
		return restaurantRepository.findAll().stream().map(i->RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public RestaurantDto getRestaurantById(String id) {
		return RestaurantMapper.mapToDto(restaurantRepository.findById(id).get());
	}

	@Override
	public boolean createRestaurant(RestaurantDto restaurantDto) {
		try {
			if(restaurantRepository.save(RestaurantMapper.mapToModel(restaurantDto))!=null) {
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
	public boolean updateRestaurant(String id, RestaurantDto restaurantDto) {
		try {
			var data = restaurantRepository.findById(id).get();
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
	public boolean deleteRestaurant(String id) {
		try {
			if(restaurantRepository.findById(id)!=null) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestaurantDto getRestaurantById(String id, String idAgent) {
		// TODO Auto-generated method stub
		return null;
	}
   
}
