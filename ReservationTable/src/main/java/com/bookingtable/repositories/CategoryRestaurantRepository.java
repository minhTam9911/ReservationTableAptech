package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.CategoryRestaurant;
import com.bookingtable.models.Role;

@Repository
public interface CategoryRestaurantRepository extends JpaRepository<CategoryRestaurant, Integer> {
	List<CategoryRestaurant> findByName(String name);
	
	@Query(value = "select * from category_restaurant where name = :name and id <> :id", nativeQuery = true)
	List<CategoryRestaurant> existName(@Param("name")String name, @Param("id") Integer id); 
}
