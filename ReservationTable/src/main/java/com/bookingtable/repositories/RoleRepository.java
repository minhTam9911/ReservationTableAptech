package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	List<Role> findByName(String name);
	
	@Query(value = "select * from role where name == :name and id <> :id", nativeQuery = true)
	List<Role> existName(@Param("name")String name, @Param("id") Integer id); 
}
