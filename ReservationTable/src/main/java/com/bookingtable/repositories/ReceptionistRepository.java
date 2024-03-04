package com.bookingtable.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Receptionist;
import com.bookingtable.models.System;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, String>  {
	Receptionist findByEmail(String email);
	@Query(value = "select * from receptionist where email = :email and id <> :id", nativeQuery = true)
	Receptionist existEmail(@Param("email")String email, @Param("id") String id);
}
