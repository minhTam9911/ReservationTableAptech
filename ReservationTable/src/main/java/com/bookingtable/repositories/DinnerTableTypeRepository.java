package com.bookingtable.repositories;

import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DinnerTableTypeRepository extends JpaRepository<DinnerTableType, Integer> {
}
