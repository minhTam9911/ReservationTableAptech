package com.bookingtable.repositories;

import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;
import com.bookingtable.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DinnerTableRepository extends JpaRepository<DinnerTable, Integer> {
    List<DinnerTable> findByDinnerTableType(DinnerTableType dinnerTableType);
<<<<<<< HEAD
    List<DinnerTable> findByRestaurant_Id(String restaurant_Id);
=======
    
    List<DinnerTable> findByRestaurantId(String restaurentId);
>>>>>>> origin/main

}
