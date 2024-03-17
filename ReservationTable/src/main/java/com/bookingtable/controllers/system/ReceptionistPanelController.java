package com.bookingtable.controllers.system;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bookingtable.dtos.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.models.Invoice;
import com.bookingtable.models.Reservation;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.InvoiceRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IReservationService;

@Controller
@RequestMapping("receptionist")
public class ReceptionistPanelController {
	@Autowired
	private IReservationService iReservationService;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@Autowired 
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	private ResultResponse<String> result = new ResultResponse<>(false, 0, "");

	@GetMapping("index")
	public String index(Model  model, Principal principal) {
		var reservation = iReservationService.getAllReservationForReceptionist(principal.getName());
		for(var i : reservation) {
			if(i.getBookingDate().isBefore(LocalDate.now())) {
				if(i.getReservationStatus().getId() == 2) {
					iReservationService.changeReservationStatusFinnished(i.getId());
				}if(i.getReservationStatus().getId()==1) {
					iReservationService.changeReservationStatusCancel(i.getId());
				}
			}
		}
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		model.addAttribute("data", iReservationService.getAllReservationForReceptionist(principal.getName()));
		return "receptionist/index";
	}
	@GetMapping("change-status/{id}")
	public String changeStatus(@PathVariable("id") String id) {
		iReservationService.changeReservationStatusConfirmed(id);
		return "redirect:/receptionist/index";
	}
	
	
	@GetMapping("dinner-table")
	public String dinnerTable(Model model, Principal principal) {
		var receptionist = receptionistRepository.findByEmail(principal.getName());
		var dinnerTables = dinnerTableRepository.findByRestaurant_Id(receptionist.getRestaurant().getId());
		model.addAttribute("data",dinnerTables);
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		return "receptionist/dinnerTable";
	}
	
	@GetMapping("dinner-table/invoice/{id}")
	public String invoiceDinnerTable(Model model, Principal principal, @PathVariable("id") Integer id) {
		var receptionist = receptionistRepository.findByEmail(principal.getName());
		var invoices = invoiceRepository.findAll();
		List<Invoice> data = new ArrayList<>();
		for(var i : invoices) {
			if(i.getReservation().getDinnerTable().getId()== id) {
				data.add(i);
			}
		}
		model.addAttribute("data",data);
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		return "receptionist/invoiceDinnerTable";
	}
	
	@GetMapping("dinner-table/invoice/detail/{id}")
	public String detalInvoiceDinnerTable(Model model, Principal principal, @PathVariable("id") String id) {
		var receptionist = receptionistRepository.findByEmail(principal.getName());
		var invoices = invoiceRepository.findAll();
		Invoice data = new Invoice();
		for(var i : invoices) {
			if(i.getId().equals(id)) {
				data = i;
			}
		}
		model.addAttribute("data",data);
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		return "receptionist/detailCustomerDinnerTable";
	}
	

	@GetMapping("invoice")
	public String invoice(Model model, Principal principal) {
		var receptionist = receptionistRepository.findByEmail(principal.getName());
		var invoices = invoiceRepository.findAll();
		List<Invoice> data = new ArrayList<Invoice>();
		for(var i : invoices) {
			if(i.getReservation().getRestaurant().getId().equals(receptionist.getRestaurant().getId())) {
				data.add(i);
			}
		}
		model.addAttribute("msg", result);
		model.addAttribute("data",data);
		result = new ResultResponse<>(false,0,"");
		return "receptionist/invoiceRestaurant";
	}
}
