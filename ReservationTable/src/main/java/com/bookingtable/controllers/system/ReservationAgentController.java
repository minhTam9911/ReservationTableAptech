package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.IReservationAgentService;
import com.bookingtable.servicies.IRoleService;
import com.bookingtable.servicies.ISystemService;

import jakarta.validation.Valid;

import java.security.Principal;

@Controller
@RequestMapping("staff/reservationAgent")
public class ReservationAgentController {

	
	@Autowired
	private IReservationAgentService reservationAgentService;

	@Autowired 
	private IRoleService roleService;


	private ResultResponse<ReservationAgentDto> response = new ResultResponse<>();

	public ReservationAgentController() {
		this.response = new ResultResponse<>(new ReservationAgentDto());
	}

    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {


    	model.addAttribute("data", reservationAgentService.getAllReservationAgents());
    	if(response.getMessage().getEmail() !=null) {
    		if(response.isStatus()) {
    			model.addAttribute("msg",true);
    		}if(response.isStatus() == false) {
    			model.addAttribute("msg",response.getMessage().getEmail());
    		}
    	}
        return "staff/reservationAgent/index";
    }

    
	
	@GetMapping("create")
	public String create(Model model,RedirectAttributes attributes) {
		var reservationAgentDto = new ReservationAgentDto(); 
		model.addAttribute("reservationAgentDto", reservationAgentDto);
		if(response.isStatus()) {
			model.addAttribute("msg",true);
			return "staff/reservationAgent/create";
		}

		model.addAttribute("msg",response.getMessage().getEmail());
		return "staff/reservationAgent/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("reservationAgentDto") ReservationAgentDto reservationAgentDto ,
			BindingResult bindingResult, Principal principal) {
	
		  var roleData = roleService.getRoleById(3);
		  reservationAgentDto.setRoleDto(roleData);
		  System.out.println(principal.getName());
		if(bindingResult.hasErrors()) {
			return "staff/reservationAgent/create";
		}
		var response = reservationAgentService.createReservationAgent(reservationAgentDto, principal.getName());
		if(response.isStatus()) {
			this.response.setStatus(true);
			return "redirect:/staff/reservationAgent/index";
		}else {
			this.response.setMessage(new ReservationAgentDto(response.getMessage().getEmail()));
			return "redirect:/staff/reservationAgent/create";
			
		}
		
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") String id) {
		var reservationAgentDto = reservationAgentService.getReservationAgentById(id);
		model.addAttribute("reservationAgentDto", reservationAgentDto);
		return "staff/reservationAgent/edit";
	}
	@PostMapping("update/save")
	public String updateProcess(@Valid @ModelAttribute("reservationAgentDto") ReservationAgentDto reservationAgentDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "staff/reservationAgent/edit";
		}
		var roleData = roleService.getRoleById(3);
		  reservationAgentDto.setRoleDto(roleData);
		var response = reservationAgentService.updateReservationAgent(reservationAgentDto.getId(),reservationAgentDto);
		if(response.isStatus()) {
			this.response.setStatus(true);;
			return "redirect:/staff/reservationAgent/index";
		}else {
			this.response.setMessage(new ReservationAgentDto(response.getMessage().getEmail()));
			 return "staff/reservationAgent/edit";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes attributes) {
		var response = reservationAgentService.deleteReservationAgent(id);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", true);
			return "redirect:/staff/reservationAgent/index";
		}else {
			attributes.addFlashAttribute("msg", response.getMessage().getEmail());
			return "redirect:/staff/reservationAgent/index";
		}
	}
	@GetMapping("change/status/{id}")
	public String chageStatus(RedirectAttributes attributes, @PathVariable("id") String id) {
		var check = reservationAgentService.changeStatus(id);
		attributes.addFlashAttribute("msg",check);
		return "redirect:/staff/reservationAgent/index";
	}
	
	
}
