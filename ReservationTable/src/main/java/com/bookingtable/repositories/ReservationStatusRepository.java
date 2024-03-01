package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Reservation;
import com.bookingtable.models.ReservationStatus;
import com.bookingtable.models.Role;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer>  {
	List<ReservationStatus> findByStatusIgnoreCase(String status);
	@Query(value = "select * from reservation_status  where status = :status and id <> :id", nativeQuery = true)
	List<ReservationStatus> existStatus(@Param("status")String status, @Param("id") Integer id); 
}
