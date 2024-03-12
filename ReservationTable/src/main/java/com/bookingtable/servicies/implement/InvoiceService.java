package com.bookingtable.servicies.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.InvoiceDto;import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.Invoice;
import com.bookingtable.models.Rate;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.InvoiceRepository;
import com.bookingtable.repositories.RateRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.servicies.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ReceptionistRepository receptionistRepository;
	@Autowired 
	private RateRepository rateRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	
	@Override
	public List<Invoice> getList() {
		return invoiceRepository.findAll();
	}

	@Override
	public Invoice getById(String id,String idCustomer) {
		var check = invoiceRepository.findById(id).get();
		if(check.getReservation().getCustomer().getEmail().equals(idCustomer)) {
			return check;
		}
		return null;
	}

	@Override
	public List<Invoice> getByCustomer(String idCustomer) {
		List<Invoice> list = new ArrayList<>();
		var invoices = invoiceRepository.findAll();
		for(var i : invoices) {
			if(i.getReservation().getCustomer().getEmail().equals(idCustomer)) {
				list.add(i);
			}
		}
		return list;
	}

	@Override
	public List<Invoice> getByRestaurant(String idRestaurant,String idAgent) {
		List<Invoice> list = new ArrayList<>();
		var invoices = invoiceRepository.findAll();
		for(var i : invoices) {
			if(i.getReservation().getRestaurant().getId().equals(idRestaurant)) {
				if(i.getReservation().getRestaurant().getReservationAgent().getEmail().equals(idAgent)) {
					list.add(i);
				}
				else{
					var resceptionist = receptionistRepository.findByEmail(idAgent);
					if(resceptionist.getRestaurant().getId().equals(i.getReservation().getRestaurant().getId())) {
						list.add(i);
					}
				}
			}
		}
		return list;
	}

	@Override
	public boolean insert(Invoice invoice) {
		var invoiceSaveChages = invoiceRepository.save(invoice);
		var rate = new Rate();
		rate.setStatus(true);
		rate.setDinnerTable(dinnerTableRepository.findById(invoiceSaveChages.getReservation().getDinnerTable().getId()).get());
		rate.setCustomer(invoiceSaveChages.getReservation().getCustomer());
		rateRepository.save(rate);
		return true;
	}

}
