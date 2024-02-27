package com.bookingtable.repositories;

import com.bookingtable.models.DinnerTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DinnerTableRepository extends JpaRepository<DinnerTable, Integer> {
}
