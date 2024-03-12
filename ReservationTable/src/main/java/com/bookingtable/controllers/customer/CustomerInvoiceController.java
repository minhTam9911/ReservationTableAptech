package com.bookingtable.controllers.customer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.IInvoiceService;

@Controller
@RequestMapping("customer/invoice")
public class CustomerInvoiceController {

	@Autowired
	private IInvoiceService iInvoiceService;

	@GetMapping("index")
	public String index(Model model, Principal principal) {
		if(principal.getName() !=null || !principal.getName().isEmpty()) {
			model.addAttribute("data",iInvoiceService.getByCustomer(principal.getName()));
			return "customer/invoice/index";
		}
		return "account/accessDined";
	}
	
}
