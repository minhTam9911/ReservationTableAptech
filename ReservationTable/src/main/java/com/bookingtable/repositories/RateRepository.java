package com.bookingtable.repositories;

import com.bookingtable.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Integer> {
}
