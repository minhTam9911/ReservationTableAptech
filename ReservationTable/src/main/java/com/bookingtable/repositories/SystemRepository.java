package com.bookingtable.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.System;

import jakarta.websocket.server.PathParam;

@Repository
public interface SystemRepository extends JpaRepository<System, UUID> {

	//@Query(value = "select * from system s where  s.role.id <> 1", nativeQuery = true)
	List<System> findByRoleIdNot(Integer id);
	
}
