package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.InvoiceDetail;
import com.bookingtable.servicies.IInvoiceService;

@Controller
@RequestMapping("customer/invoice")
public class CustomerInvoiceController {

	@Autowired
	private IInvoiceService iInvoiceService;

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
		var i = iInvoiceService.getById(id, principal.getName());
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
		model.addAttribute("data", invoice);
		return "customer/invoice/detail";
	}
	
}
