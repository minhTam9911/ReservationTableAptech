package com.bookingtable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Collection;
import com.bookingtable.models.Invoice;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer>  {

	List<Collection> findByCustomerEmail(String email);
	Collection findByCustomerIdAndRestaurantId(String restaurantId, String customerId);

}
