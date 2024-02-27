package com.bookingtable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Receptionist;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, String>  {
 
}
