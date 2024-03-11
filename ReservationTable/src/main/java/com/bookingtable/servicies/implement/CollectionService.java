package com.bookingtable.servicies.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookingtable.dtos.CollectionDto;
import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.servicies.ICollectionService;

@Service
public class CollectionService implements ICollectionService {

	@Override
	public CollectionDto getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CollectionDto> getByCustomer(String idCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(InvoiceDto invoiceDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
