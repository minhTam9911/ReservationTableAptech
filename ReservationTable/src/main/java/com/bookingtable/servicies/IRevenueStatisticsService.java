package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.models.RevenueStatistics;

public interface IRevenueStatisticsService {

	List<RevenueStatistics> findAll();
	boolean create(RevenueStatistics revenueStatistics);
}
