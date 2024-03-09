package com.bookingtable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Controller

public class AccountController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private ResultResponse<String> result = new ResultResponse<>(false,"");
	

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Principal principal,
			RedirectAttributes redirectAttributes, Model model) {
		if(principal == null){
			var customerDto = new CustomerDto();
			model.addAttribute("customerDto", customerDto);
			if (error != null) {
				model.addAttribute("msg", "Username and password invalid");
			}

			return "account/login";
		}
		return "redirect:/accessDenied";

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
	public String verifyActive(@PathParam("email")String email, @PathParam("securityCode") String securityCode,RedirectAttributes attributes) {
		if(customerService.changeStatus(email, securityCode)) {
			attributes.addFlashAttribute("msg", "Success activation");
		}else {
			attributes.addFlashAttribute("msg", "Failure activation");
		}
		return "redirect:/login";
	}
	
	

	@GetMapping("/forgot-password")
	public String forgot(Model model) {
		if(!result.getMessage().isEmpty()) {
			model.addAttribute("msg",result.getMessage());
			result = new ResultResponse<>(false,"");
		}
		return "account/forgotPassword";
	}
	

	@GetMapping("/reset-password")
	public String resetPassword(HttpSession session, Model model) {
		if(session.getAttribute("email")!=null) {
			if(!result.getMessage().isEmpty()) {
				model.addAttribute("msg",result.getMessage());
			}
			result = new ResultResponse<>(false,"");
			return "account/resetPassword";
		}
		return "account/accessDenied";
	}
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "account/accessDenied";
	}
	@GetMapping("/verify-code")
	public String verifyCode(HttpSession session,Model model) {
		if(session.getAttribute("email")!=null) {
			if(!result.getMessage().isEmpty()) {
				model.addAttribute("msg",result.getMessage());
			}
			result = new ResultResponse<>(false,"");
			return "account/verificationCode";
	}
		return "account/accessDenied";
	}
	
	
	@PostMapping("/forgot-password/submit")
	public String forgot(RedirectAttributes attributes,Model model,HttpSession session, @PathParam("email") String email) {
		var check = accountService.forgotPassword(email);
		if(check.isStatus()) {
			model.addAttribute("msg", check.getMessage());
			session.setAttribute("email", email);
			return "account/verificationCode";
		}
		result.setMessage(check.getMessage());
		return "redirect:/forgot-password";
		
	}
	@PostMapping("/verify/submit")
	public String verifyCode(RedirectAttributes attributes,Model model,HttpSession session, @PathParam("code") String[] code) {
		if(session.getAttribute("email") != null) {
			String verifyCode = "";
			for(var i : code) {
				verifyCode+=i;
			}
			var check = accountService.verifyCode(session.getAttribute("email").toString(), verifyCode);
			if(check.isStatus()) {
				
				return "redirect:/reset-password";
			}
			result.setMessage(check.getMessage());
			return "redirect:/verify-code";
		}
		return "account/accessDenied";
		
	}
	@PostMapping("/reset-password/save")
	public String resetPassword(RedirectAttributes attributes,Model model,HttpSession session, @PathParam("newPassword") String newPassword,  @PathParam("confirmPassword") String confirmPassword) {
		if(session.getAttribute("email") != null) {
			if(!newPassword.equals(confirmPassword)) {
				result.setMessage( "Confirm password does not match new password");
				return "redirect:/reset-password";
			}
			var hashPassword = bCryptPasswordEncoder.encode(newPassword);
			var check = accountService.saveResetPassword(session.getAttribute("email").toString(), hashPassword);
			if(check.isStatus()) {
				session.removeAttribute("email");
				result.setMessage(check.getMessage());
				return "redirect:/login";
			}
			result.setMessage(check.getMessage());
			return "redirect:/reset-password";
		}
		return "account/accessDenied";
	}
}
