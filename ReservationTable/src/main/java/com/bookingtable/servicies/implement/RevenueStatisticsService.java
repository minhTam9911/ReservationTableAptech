package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.models.RevenueStatistics;
import com.bookingtable.repositories.RevenueStatisticsRepository;
import com.bookingtable.servicies.IRestaurantStatisticalService;
import com.bookingtable.servicies.IRevenueStatisticsService;

@Service
public class RevenueStatisticsService implements IRevenueStatisticsService {

	@Autowired
	private RevenueStatisticsRepository repository;

	@Override
	public List<RevenueStatistics> findAll() {
		return repository.findAll();
	}

	@Override
	public boolean create(RevenueStatistics revenueStatistics) {
		try {
			if(repository.save(revenueStatistics)!=null) {
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
