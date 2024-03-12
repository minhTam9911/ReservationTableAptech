package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.CollectionDto;
import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.models.Collection;
import com.bookingtable.models.Invoice;

public interface ICollectionService {

	
	Collection getById(Integer id);
	
	List<Collection> getByCustomer(String idCustomer);
	
	boolean insert(Collection collection);
	
	boolean delete(Integer id,String idCustomer);
	
}
