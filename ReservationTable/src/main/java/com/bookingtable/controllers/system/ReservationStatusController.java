package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.ReservationStatusDto;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.IReservationStatusService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("staff/reservationStatus")
public class ReservationStatusController {


	@Autowired
	private IReservationStatusService reservationStatusService;
	
	@GetMapping({"index",""})
	public String index(Model model) {
		model.addAttribute("reservationStatuses", reservationStatusService.getAllReservationStatuses());
		return "staff/reservationStatus/index";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		ReservationStatusDto reservationStatusDto = new ReservationStatusDto();
		model.addAttribute("reservationStatusDto", reservationStatusDto);
		return "staff/reservationStatus/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("reservationStatusDto") ReservationStatusDto reservationStatusDto ,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "staff/reservationStatus/create";
		}
		var response = reservationStatusService.createReservationStatus(reservationStatusDto);
		if(response.isStatus()) {
			return "redirect:/staff/reservationStatus/index";
		}else {
			  
		       bindingResult.addError(new FieldError("roleDto","name", response.getMessage().getStatus()));
		       return "staff/reservationStatus/create";
		}
		
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("reservationStatusDto", reservationStatusService.getReservationStatusById(id));
		return "staff/reservationStatus/edit";
	}
	@PostMapping("updateProcess")
	public String updateProcess(@Valid @ModelAttribute("reservationStatusDto") ReservationStatusDto reservationStatusDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "staff/reservationStatus/edit";
		}
		var response = reservationStatusService.updateReservationStatus(reservationStatusDto.getId(),reservationStatusDto);
		if(response.isStatus()) {
			
			return "redirect:/staff/reservationStatus/index";
		}else {
			 bindingResult.addError(new FieldError("roleDto","name", response.getMessage().getStatus()));
		       return "staff/reservationStatus/edit";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		var response = reservationStatusService.deleteReservationStatus(id);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/staff/reservationStatus/index";
		}else {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/staff/reservationStatus/index";
		}
	}
	
}
