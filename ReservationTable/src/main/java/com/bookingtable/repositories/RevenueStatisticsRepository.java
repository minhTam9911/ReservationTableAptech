package com.bookingtable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.RevenueStatistics;

@Repository
public interface RevenueStatisticsRepository extends JpaRepository<RevenueStatistics, String> {

}
