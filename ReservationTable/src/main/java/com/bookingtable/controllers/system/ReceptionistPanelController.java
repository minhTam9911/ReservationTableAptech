package com.bookingtable.controllers.system;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.IReservationService;

@Controller
@RequestMapping("receptionist")
public class ReceptionistPanelController {
	@Autowired
	private IReservationService iReservationService;
	
	@GetMapping("index")
	public String index(Model  model, Principal principal) {
		model.addAttribute("data", iReservationService.getAllReservationForReceptionist(principal.getName()));
		return "receptionist/index";
	}
	
}
