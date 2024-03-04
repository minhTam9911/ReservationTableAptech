package com.bookingtable.repositories;

import com.bookingtable.models.Guest;
import com.bookingtable.models.System;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, String> {
	Guest findByEmail(String email);
	@Query(value = "select * from guest where email = :email and id <> :id", nativeQuery = true)
	Guest existEmail(@Param("email")String email, @Param("id") String id);
}
