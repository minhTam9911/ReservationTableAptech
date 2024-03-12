package com.bookingtable.servicies;

import java.util.List;

import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.models.Invoice;

public interface IInvoiceService {

	List<Invoice> getList();
	
	Invoice getById(String id,String idCustomer);
	
	List<Invoice> getByCustomer(String idCustomer);
	
	List<Invoice> getByRestaurant(String idRestaurant,String idAgent);
	
	boolean insert(Invoice Invoice);
	
}
