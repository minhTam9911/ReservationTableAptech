package com.bookingtable.controllers.customer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.ICollectionService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("customer/collection")
public class CollectionController {

	@Autowired
	private ICollectionService collectionService;
	
	@GetMapping("index")
	public String index(Model model, Principal principal) {
		if(principal !=null) {
			model.addAttribute("data",collectionService.getByCustomer(principal.getName()));
			return "customer/collection/index";
		}
		return "account/accessDined";
	}
	
//	@GetMapping("delete/{id}")
//	public String delete(Model model,@PathVariable("id") Integer id, Principal principal) {
//		if(principal !=null) {
//			collectionService.delete(id, principal.getName());
//			return "redirect:/customer/collection/index";
//		}
//		return "account/accessDined";
//	}
	
}
