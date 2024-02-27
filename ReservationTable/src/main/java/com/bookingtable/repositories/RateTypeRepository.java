package com.bookingtable.repositories;

import com.bookingtable.models.RateType;
import com.bookingtable.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateTypeRepository extends JpaRepository<RateType, Integer> {
}
