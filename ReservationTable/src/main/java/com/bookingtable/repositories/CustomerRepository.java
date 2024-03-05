package com.bookingtable.repositories;


import com.bookingtable.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	Customer findByEmail(String email);
	@Query(value = "select * from guest where email = :email and id <> :id", nativeQuery = true)
	Customer existEmail(@Param("email")String email, @Param("id") String id);
}
