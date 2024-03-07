package com.bookingtable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import jakarta.websocket.server.PathParam;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class AccountController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IRoleService roleService;
	
	private ResultResponse<String> result = new ResultResponse<>(false,null);
	
	
	
	public AccountController() {
		super();
		this.result = new ResultResponse<>(false,null);
		
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			RedirectAttributes redirectAttributes, Model model) {
		var customerDto = new CustomerDto();
		model.addAttribute("customerDto", customerDto);
		if (error != null) {
			model.addAttribute("msg", "Username and password invalid");
		}
		
		return "account/login";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("customerDto") CustomerDto customerDto ,
			BindingResult bindingResult,RedirectAttributes attributes,Model model) {
		var roleData = roleService.getRoleById(1);
		customerDto.setCreated(LocalDate.now());
		customerDto.setRoleDto(roleData);
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "Form input invalid");
			return "account/login";
		}
		var response = customerService.createCustomer(customerDto);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", "Please check your email again to activate your account");
			return "redirect:/login";
		}else {
			attributes.addFlashAttribute("msg",response.getMessage().getEmail()==null?null: response.getMessage().getEmail());
			return "redirect:/login";
			
		}
		
	}
	@GetMapping("verify")
	public String verify(@PathParam("email")String email, @PathParam("securityCode") String securityCode,RedirectAttributes attributes) {
		if(customerService.changeStatus(email, securityCode)) {
			attributes.addFlashAttribute("msg", "Success activation");
		}else {
			attributes.addFlashAttribute("msg", "Failure activation");
		}
		return "redirect:/login";
	}
}
