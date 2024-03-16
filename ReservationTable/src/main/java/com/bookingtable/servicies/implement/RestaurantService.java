package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.CategoryRestaurantMapper;
import com.bookingtable.mappers.DinnerTableMapper;
import com.bookingtable.mappers.RestaurantMapper;
import com.bookingtable.models.CategoryRestaurant;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.Restaurant;
import com.bookingtable.repositories.CategoryRestaurantRepository;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService implements IRestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private CategoryRestaurantRepository categoryRestaurantRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired 
	private ReceptionistRepository receptionistRepository;

	@Override
	public List<RestaurantDto> getAllRestaurants() {
		return restaurantRepository.findAll().stream().map(i -> RestaurantMapper.mapToDto(i))
				.collect(Collectors.toList());
	}
	@Override
	public List<RestaurantDto> getRestaurantByName(String name) {
		List<Restaurant> restaurantByName = restaurantRepository.findByNameLike(name);
		if (restaurantByName.size()>0){
			return restaurantRepository.findByNameLike(name).stream().map(i-> RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
		}
		return restaurantRepository.findAll().stream().map(i-> RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}
	@Override
	public RestaurantDto getRestaurantById(String id) {
		return RestaurantMapper.mapToDto(restaurantRepository.findById(id).get());
	}

	@Override
	public ResultResponse<String> createRestaurant(RestaurantDto restaurantDto, String emailCreatedBy) {
		try {
			var check = reservationAgentRepository.findByEmail(emailCreatedBy);
			var check2 = restaurantRepository.findByReservationAgentEmail(emailCreatedBy);
			if (check2.size() >= check.getTotalRestaurant()) {
				return new ResultResponse<String>(true, 2,
						"The total number of restaurants you have registered is now sufficient");
			}
			var data = RestaurantMapper.mapToModel(restaurantDto);
			data.setCategoryRestaurant(categoryRestaurantRepository.findById(restaurantDto.getCategoryId()).get());
			data.setReservationAgent(check);
			var saveChange = restaurantRepository.save(data);
			if (saveChange != null) {
				return new ResultResponse<String>(true, 1, saveChange.getId());
			} else {
				return new ResultResponse<String>(true, 2, "Process Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<String>(true, 2, e.getMessage());
		}
	}

	@Override
	public ResultResponse<String> updateRestaurant(String id, RestaurantDto restaurantDto, String emailCreatedBy) {
		try {
			var data = restaurantRepository.findById(id).get();
			if (data == null || !data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
				return new ResultResponse<String>(true, 2, "Process Failure");
			}
			data.setName(restaurantDto.getName());
			data.setAddress(restaurantDto.getAddress());
			data.setCity(restaurantDto.getCity());
			data.setDistrict(restaurantDto.getDistrict());
			data.setWard(restaurantDto.getWard());
			data.setShortDescription(restaurantDto.getShortDescription());
			data.setDescription(restaurantDto.getDescription());
			data.setCompanyMail(restaurantDto.getCompanyMail());
			data.setMainPhoneNumber(restaurantDto.getMainPhoneNumber());
			data.setFaxNumber(restaurantDto.getFaxNumber());
			data.setCreated(LocalDate.now());
			data.setUpdated(LocalDate.now());
			data.setCategoryRestaurant(CategoryRestaurantMapper.mapToModel(restaurantDto.getCategoryRetaurantDto()));
			var saveChange = restaurantRepository.save(data);
			if (saveChange != null) {
				return new ResultResponse<String>(true, 1, saveChange.getId());
			} else {
				return new ResultResponse<String>(true, 2, "Process Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<String>(true, 2, e.getMessage());
		}
	}

	@Override
	public ResultResponse<String> deleteRestaurant(String id, String emailCreatedBy) {
		try {
			if (restaurantRepository.findById(id) != null) {
				var dinnerTables = dinnerTableRepository.findByRestaurant_Id(id);
				for (var dinnerTable : dinnerTables) {
					dinnerTableRepository.deleteById(dinnerTable.getId());
				}
				restaurantRepository.deleteById(id);
				return new ResultResponse<String>(true, 1, "Process Successfully");
			} else {
				return new ResultResponse<String>(true, 2, "Process Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<String>(true, 2, e.getMessage());
		}
	}

	@Override
	public List<RestaurantDto> getAllRestaurantsForAgent(String idAgent) {
		return restaurantRepository.findByReservationAgentEmail(idAgent).stream().map(i -> RestaurantMapper.mapToDto(i))
				.collect(Collectors.toList());
	}

	@Override
	public RestaurantDto getRestaurantById(String id, String idAgent) {
		try {
			var data = restaurantRepository.findById(id).get();
			if (data != null && data.getReservationAgent().getEmail().equalsIgnoreCase(idAgent)) {
				return RestaurantMapper.mapToDto(data);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RestaurantDto> getAllRestaurantsWithCategory(Integer categoryId) {
		return restaurantRepository.findByCategoryRestaurantId(categoryId).stream()
				.map(i -> RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public List<RestaurantDto> getAllCategory(Integer id) {
		var restaurants = restaurantRepository.findAll();
		List<Restaurant> list = new ArrayList<>();
		for (var i : restaurants) {
			if (i.getCategoryRestaurant().getId() == id) {
				list.add(i);
			}
		}
		return list.stream().map(i -> RestaurantMapper.mapToDto(i)).collect(Collectors.toList());
	}

	@Override
	public ResultResponse<String> changeStatus(String id, String emailCreatedBy) {
		try {
			var data = restaurantRepository.findById(id).get();
			if (data == null) {
				return new ResultResponse<String>(true, 2, "Proccess Failure");
			}
			if (data != null && data.getReservationAgent().getEmail().equalsIgnoreCase(emailCreatedBy)) {
				data.setActive(!data.isActive());
				if (restaurantRepository.save(data) != null)
					return new ResultResponse<String>(true,1,"Proccess Successlly");
			}
			var reception = receptionistRepository.findByEmail(emailCreatedBy);
			if (data != null && reception.getRestaurant().getId().equals(data.getId())) {
				data.setActive(!data.isActive());
				if (restaurantRepository.save(data) != null)
					return new ResultResponse<String>(true,1,"Proccess Successlly");
			}
			return new ResultResponse<String>(true, 2,"Proccess Failure");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultResponse<String>(true, 2, "Proccess Failure");
		}
	}

}
