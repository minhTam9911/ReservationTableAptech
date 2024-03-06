package com.bookingtable.controllers.system;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.ReservationAgentDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.servicies.IReceptionistService;
import com.bookingtable.servicies.IReservationAgentService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("partner/receptionist")
public class ReceptionistController {
	@Autowired
	private IReceptionistService receptionistService;

	@Autowired 
	private IRoleService roleService;
	
	private ResultResponse<ReceptionistDto> response = new ResultResponse<>();
	public  ReceptionistController() {
		this.response = new ResultResponse<>(new ReceptionistDto());
	}

    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model, Principal principal) {


    	model.addAttribute("data", receptionistService.getAllReceptionist(principal.getName()));
    	if(response.getMessage().getEmail() !=null) {
    		if(response.isStatus()) {
    			model.addAttribute("msg",true);
    		}if(response.isStatus() == false) {
    			model.addAttribute("msg",response.getMessage().getEmail());
    		}
    	}
        return "partner/receptionist/index";
    }

    
	
	@GetMapping("create")
	public String create(Model model,RedirectAttributes attributes) {
		var receptionistDto = new ReceptionistDto(); 
		model.addAttribute("receptionistDto", receptionistDto);
		if(response.isStatus()) {
			model.addAttribute("msg",true);
			return "partner/receptionist/create";
		}

		model.addAttribute("msg",response.getMessage().getEmail());
		return "partner/receptionist/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("receptionistDto") ReceptionistDto receptionistDto ,
			BindingResult bindingResult,Principal principal) {
	
		  var roleData = roleService.getRoleById(4);
		  receptionistDto.setRoleDto(roleData);
		if(bindingResult.hasErrors()) {
			return "partner/receptionist/create";
		}
		var response = receptionistService.createReceptionist(receptionistDto,principal.getName());
		if(response.isStatus()) {
			this.response.setStatus(true);
			return "redirect:/partner/receptionist/index";
		}else {
			this.response.setMessage(new ReceptionistDto(response.getMessage().getEmail()));
			return "redirect:/partner/receptionist/create";
			
		}
		
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") String id,Principal principal) {
		var receptionistDto = receptionistService.getReceptionistById(id,principal.getName());
		model.addAttribute("receptionistDto", receptionistDto);
		return "partner/receptionist/edit";
	}
	@PostMapping("update/save")
	public String updateProcess(@Valid @ModelAttribute("receptionistDto") ReceptionistDto receptionistDto, BindingResult bindingResult,Principal principal) {
		if(bindingResult.hasErrors()) {
			return "partner/receptionist/edit";
		}
		var roleData = roleService.getRoleById(4);
		receptionistDto.setRoleDto(roleData);
		var response = receptionistService.updateReceptionist(receptionistDto.getId(),receptionistDto, principal.getName());
		if(response.isStatus()) {
			this.response.setStatus(true);;
			return "redirect:/partner/receptionist/index";
		}else {
			this.response.setMessage(new ReceptionistDto(response.getMessage().getEmail()));
			 return "partner/receptionist/edit";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes attributes,Principal principal) {
		var response = receptionistService.deleteReceptionist(id, principal.getName());
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", true);
			return "redirect:/partner/receptionist/index";
		}else {
			attributes.addFlashAttribute("msg", response.getMessage().getEmail());
			return "redirect:/partner/receptionist/index";
		}
	}
	@GetMapping("change/status/{id}")
	public String chageStatus(RedirectAttributes attributes, @PathVariable("id") String id, Principal principal) {
		var check = receptionistService.changeStatus(id, principal.getName());
		attributes.addFlashAttribute("msg",check);
		return "redirect:/partner/receptionist/index";
	}
	
}
