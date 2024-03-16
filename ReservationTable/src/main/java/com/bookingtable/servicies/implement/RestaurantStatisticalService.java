package com.bookingtable.servicies.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.models.RestaurantStatistical;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.repositories.RestaurantStatisticalRepository;
import com.bookingtable.servicies.IRestaurantStatisticalService;

@Service
public class RestaurantStatisticalService implements IRestaurantStatisticalService {

	@Autowired
	private RestaurantStatisticalRepository restaurantStatisticalRepository;
	@Autowired
	private ReservationAgentRepository agentRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<RestaurantStatistical> findAll(String emailAgent,String restaurantId) {
		List<RestaurantStatistical> list = new ArrayList();
		var restaurants = restaurantRepository.findByReservationAgentEmail(emailAgent);

		for(var i : restaurants) {

			if(i.getId().equals(restaurantId)) {
				var statistical = restaurantStatisticalRepository.findByRestaurant(restaurantId);

				if(statistical.size()>0) {
					
					list.addAll(statistical);
				}
			}
		}
		return list;
	}

	@Override
	public boolean create(RestaurantStatistical restaurantStatistical) {
		try {
			if(restaurantStatisticalRepository.save(restaurantStatistical)!=null) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
