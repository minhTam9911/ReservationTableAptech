package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.CollectionDto;
import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.models.Collection;
import com.bookingtable.models.Invoice;

public interface ICollectionService {

	
	CollectionDto getById(Integer id);
	
	List<CollectionDto> getByCustomer(String idCustomer);
	
	boolean insert(InvoiceDto invoiceDto);
	
	boolean delete(Integer id);
	
}
