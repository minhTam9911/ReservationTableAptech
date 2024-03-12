package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.CollectionDto;
import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.models.Collection;
import com.bookingtable.models.Invoice;

public interface ICollectionService {

	
	Collection getById(Integer id);
	
	List<Collection> getByCustomer(String idCustomer);
	
	boolean insert(CollectionDto collectionDto);
	
	boolean delete(Integer id);

	public CollectionDto findByCustomerAndRestaurant(String idCustomer, String idRestaurant);



}
