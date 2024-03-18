package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.InvoiceDetail;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IInvoiceService;
import com.bookingtable.servicies.IReservationService;

@Controller
@RequestMapping("customer/invoice")
public class CustomerInvoiceController {

	@Autowired
	private IInvoiceService iInvoiceService;
	@Autowired
	private IReservationService reservationService;

	@GetMapping("index")
	public String index(Model model, Principal principal) {
		var invoices = iInvoiceService.getByCustomer(principal.getName());
		List<InvoiceDetail> listInvoice = new ArrayList();
		for(var i : invoices) {
			var invoice = new InvoiceDetail();
			invoice.setIdInvoice(i.getId());
			invoice.setAmount(i.getReservation().getDinnerTable().getDinnerTableType().getPrice());
			invoice.setFullName(i.getReservation().getCustomer().getFullName());
			invoice.setCreated(i.getReservation().getCreated());
			invoice.setRestaurant(i.getReservation().getDinnerTable().getRestaurant().getName());
			invoice.setDinnerTable(i.getReservation().getDinnerTable().getId());
			invoice.setQuantity(1);
			invoice.setCity(i.getReservation().getDinnerTable().getRestaurant().getCity());
			invoice.setDistrict(i.getReservation().getDinnerTable().getRestaurant().getDistrict());
			invoice.setWard(i.getReservation().getDinnerTable().getRestaurant().getWard());
			invoice.setAddress(i.getReservation().getDinnerTable().getRestaurant().getAddress());
			invoice.setStatus(i.getReservation().getReservationStatus().getStatus());
			invoice.setReservationId(i.getReservation().getId());
			listInvoice.add(invoice);
		}
		model.addAttribute("data", listInvoice);
		return "customer/invoice/index";
//		if(principal.getName() !=null || !principal.getName().isEmpty()) {
//			model.addAttribute("data",iInvoiceService.getByCustomer(principal.getName()));
//			return "customer/invoice/index";
//		}
//		return "account/accessDined";
	}
	
	@GetMapping("detail/{id}")
	public String detail(Model model,@PathVariable("id") String id, Principal principal) {
		var data = iInvoiceService.getById(id, principal.getName());
		model.addAttribute("data", data);
		return "customer/invoice/detail";
	}
	@GetMapping("cancel/{id}")
	public String cancel(@PathVariable("id") String id, Principal principal) {
		var i = iInvoiceService.getById(id, principal.getName());
		reservationService.changeReservationStatusCancel(i.getReservation().getId());
		return "redirect:/customer/invoice/index";
	}
}
