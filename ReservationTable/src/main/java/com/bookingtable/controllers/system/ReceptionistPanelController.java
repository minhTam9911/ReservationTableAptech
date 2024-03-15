package com.bookingtable.controllers.system;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bookingtable.dtos.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.models.Reservation;
import com.bookingtable.servicies.IReservationService;

@Controller
@RequestMapping("receptionist")
public class ReceptionistPanelController {
	@Autowired
	private IReservationService iReservationService;
	private ResultResponse<String> result = new ResultResponse<>(false, 0, "");

	@GetMapping("index")
	public String index(Model  model, Principal principal) {
		var reservation = iReservationService.getAllReservationForReceptionist(principal.getName());
		for(var i : reservation) {
			if(i.getBookingDate().isBefore(LocalDate.now()) && i.getBookingTime().isBefore(LocalTime.now())) {
				if(i.getReservationStatus().getId() == 2) {
					iReservationService.changeReservationStatusFinnished(i.getId());
				}if(i.getReservationStatus().getId()==1) {
					iReservationService.changeReservationStatusCancel(i.getId());
				}
			}
		}
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		model.addAttribute("data", iReservationService.getAllReservationForReceptionist(principal.getName()));
		return "receptionist/index";
	}
	@GetMapping("change-status/{id}")
	public String changeStatus(@PathVariable("id") String id) {
		iReservationService.changeReservationStatusConfirmed(id);
		return "redirect:/receptionist/index";
	}
	
}
