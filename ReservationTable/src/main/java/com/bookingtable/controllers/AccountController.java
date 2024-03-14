package com.bookingtable.controllers;

import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
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
	private ResultResponse<String> result = new ResultResponse<>(false,0,"");
	@Autowired
	private ISystemService systemService;

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Principal principal,
			RedirectAttributes redirectAttributes, Model model) {
		if(principal == null){
			var customerDto = new CustomerDto();
			model.addAttribute("customerDto", customerDto);
			if (error != null) {
				result.setOption(2);
				result.setStatus(true);
				result.setMessage("Username and password invalid");
				model.addAttribute("msg", result);
			}

			return "account/login";
		}
		return "redirect:/accessDenied";

	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("customerDto") CustomerDto customerDto ,
			BindingResult bindingResult,RedirectAttributes attributes,Model model) {
		var roleData = roleService.getRoleById(5);
		customerDto.setCreated(LocalDate.now());
		customerDto.setRoleDto(roleData);
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "Form input invalid");
			return "account/login";
		}
		result = customerService.createCustomer(customerDto);
			attributes.addFlashAttribute("msg", result);
			return "redirect:/login";
	}
	@GetMapping("verify")
	public String verifyActive(@PathParam("email")String email, @PathParam("securityCode") String securityCode,RedirectAttributes attributes) {
		if(customerService.changeStatus(email, securityCode)) {
			result.setOption(1);
			result.setMessage("Success activation");
			result.setStatus(true);
			
		}else {
			result.setOption(1);
			result.setMessage("Failure activation");
			result.setStatus(true);
		}
		attributes.addFlashAttribute("msg", result);
		return "redirect:/login";
	}
	
	

	@GetMapping("/forgot-password")
	public String forgot(Model model) {
		if(!result.getMessage().isEmpty()) {
			model.addAttribute("msg",result);
		}
		return "account/forgotPassword";
	}
	

	@GetMapping("/reset-password")
	public String resetPassword(HttpSession session, Model model) {
		if(session.getAttribute("email")!=null) {
			if(!result.getMessage().isEmpty()) {
				model.addAttribute("msg",result);
			};
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
				model.addAttribute("msg",result);
			}
			return "account/verificationCode";
		}
		return "account/accessDenied";
	}
	
	
	@PostMapping("/forgot-password/submit")
	public String forgot(RedirectAttributes attributes,Model model,HttpSession session, @PathParam("email") String email) {
		result = accountService.forgotPassword(email);
		if(result.getOption() == 1) {
			model.addAttribute("msg", result);
			session.setAttribute("email", email);
			return "account/verificationCode";
		}if(result.getOption() == 2) {
			model.addAttribute("msg", result);
		}
		return "redirect:/forgot-password";
		
	}
	@PostMapping("/verify/submit")
	public String verifyCode(RedirectAttributes attributes,Model model,HttpSession session, @PathParam("code") String[] code) {
		if(session.getAttribute("email") != null) {
			String verifyCode = "";
			for(var i : code) {
				verifyCode+=i;
			}
			result = accountService.verifyCode(session.getAttribute("email").toString(), verifyCode);
			if(result.getOption() == 1) {
				model.addAttribute("msg", result);
				return "redirect:/reset-password";
			}
			model.addAttribute("msg", result);
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
			result = accountService.saveResetPassword(session.getAttribute("email").toString(), hashPassword);
			if(result.getOption() == 1) {
				session.removeAttribute("email");
				model.addAttribute("msg", result);
				return "redirect:/login";
			}
			model.addAttribute("msg", result);
			return "redirect:/reset-password";
		}
		return "account/accessDenied";
	}

	@GetMapping("/profile")
	public String profileForm(Model model, Principal principal) {
		var systemDto = accountService.findByEmail(principal.getName());
		model.addAttribute("systemDto", systemDto);
		return "/account/profile";
	}
	@PostMapping("/profile/save")
	public String updateProfileProcess(@Valid @ModelAttribute("systemDto") SystemDto systemDto,
									   BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "account/profile";
		}
		var response = accountService.updateProfile(systemDto,systemDto.getEmail());
		if(response.isStatus()) {
			this.result.setStatus(true);;
			return "redirect:/profile";
		}else {
			this.result.setMessage("Update Profile success");
			return "account/profile";
		}
	}
}
