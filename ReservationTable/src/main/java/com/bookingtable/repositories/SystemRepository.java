package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Role;
import com.bookingtable.models.System;

import jakarta.websocket.server.PathParam;

@Repository
public interface SystemRepository extends JpaRepository<System, String> {

	//@Query(value = "select * from system s where  s.role.id <> 1", nativeQuery = true)
	List<System> findByRoleIdNot(Integer id);
	
	System findByEmail(String email);
	
	@Query(value = "select * from system where email = :email and id <> :id", nativeQuery = true)
	System existEmail(@Param("email")String email, @Param("id") String id);
	
}
