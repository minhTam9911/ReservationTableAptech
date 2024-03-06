package com.bookingtable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.servicies.IAccountService;
import com.bookingtable.servicies.ICustomerService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class AccountController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IRoleService roleService;
	
	private ResultResponse<Boolean> result = new ResultResponse<>();
	
	
	
	public AccountController() {
		super();
		this.result = new ResultResponse<>();
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			RedirectAttributes redirectAttributes, Model model) {
		var customerDto = new CustomerDto();
		model.addAttribute("customerDto", customerDto);
		model.addAttribute("msg",result.getMessage() );
		if (error != null) {
			redirectAttributes.addFlashAttribute("msg", "Username and password invalid");
		}
		return "account/login";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("customerDto") CustomerDto customerDto ,
			BindingResult bindingResult) {
		 var roleData = roleService.getRoleById(5);
		customerDto.setCreated(LocalDate.now());
		customerDto.setRoleDto(roleData);
		if(bindingResult.hasErrors()) {
			return "account/login";
		}
		var response = customerService.createCustomer(customerDto);
		if(response.isStatus()) {
			this.result.setMessage(true);
			return "redirect:/login";
		}else {
			this.result.setMessage(false);
			return "redirect:/login";
			
		}
		
	}
}
