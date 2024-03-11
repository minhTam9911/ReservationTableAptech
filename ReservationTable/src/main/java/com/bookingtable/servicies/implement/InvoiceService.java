package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.models.Invoice;
import com.bookingtable.servicies.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {

	@Override
	public List<Invoice> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invoice> getByCustomer(String idCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invoice> getByRestaurant(String idRestaurant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(InvoiceDto invoiceDto) {
		// TODO Auto-generated method stub
		return false;
	}

}
