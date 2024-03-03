package com.bookingtable.controllers.system;

import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.ReservationStatusDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.IRoleService;
import com.bookingtable.servicies.ISystemService;
import com.bookingtable.servicies.implement.RoleService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("admin/panel" )
public class SystemController {
	@Autowired
	private ISystemService systemService;

	@Autowired 
	private IRoleService roleService;
	
	private ResultResponse<SystemDto> response = new ResultResponse<>();
	public SystemController() {
		this.response = new ResultResponse<>(new SystemDto());
	}



    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {


    	model.addAttribute("data", systemService.getAllSystems());
    	if(response.getMessage().getEmail() !=null) {
    		if(response.isStatus()) {
    			model.addAttribute("msg",true);
    		}if(response.isStatus() == false) {
    			model.addAttribute("msg",response.getMessage().getEmail());
    		}
    	}
        return "admin/panel/staff/index";
    }

    
	
	@GetMapping("create")
	public String create(Model model,RedirectAttributes attributes) {
		var systemDto = new SystemDto(); 
		systemDto.setRoleList(roleService.getAllRoles());
		model.addAttribute("systemDto", systemDto);
		if(response.isStatus()) {
			model.addAttribute("msg",true);
			return "admin/panel/staff/create";
		}
		
		model.addAttribute("msg",response.getMessage().getEmail());
		return "admin/panel/staff/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("systemDto") SystemDto systemDto ,
			BindingResult bindingResult) {
	
		  var roleData = roleService.getRoleById(systemDto.getRoleId());
		  systemDto.setRoleDto(roleData);
		 
		System.out.println(bindingResult.getAllErrors().toString());
		
		if(bindingResult.hasErrors()) {
			systemDto.setRoleList(roleService.getAllRoles());
			return "admin/panel/staff/create";
		}
		var response = systemService.insertSystem(systemDto);
		if(response.isStatus()) {
			this.response.setStatus(true);
			return "redirect:/admin/panel/index";
		}else {
			this.response.setMessage(new SystemDto(response.getMessage().getEmail()));
			systemDto.setRoleList(roleService.getAllRoles());
			return "redirect:/admin/panel/create";
			
		}
		
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") UUID id) {
		model.addAttribute("systemDto", systemService.getSystemsById(id));
		model.addAttribute("roleDto", roleService.getAllRoles());
		return "admin/panel/staff/edit";
	}
	@PostMapping("update/save")
	public String updateProcess(@Valid @ModelAttribute("systemDto") SystemDto systemDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "admin/panel/staff/edit";
		}
		var response = systemService.updateSystem(systemDto.getId(),systemDto);
		if(response.isStatus()) {
			this.response.setStatus(true);;
			return "redirect:/admin/panel/index";
		}else {
			this.response.setMessage(new SystemDto(response.getMessage().getEmail()));
			systemDto.setRoleList(roleService.getAllRoles());
			 return "admin/panel/staff/edit";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") UUID id, RedirectAttributes attributes) {
		var response = systemService.deleteSystem(id);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", true);
			return "redirect:/admin/panel/index";
		}else {
			attributes.addFlashAttribute("msg", response.getMessage().getEmail());
			return "redirect:/admin/panel/index";
		}
	}
	@GetMapping("change/status/{id}")
	public String chageStatus(RedirectAttributes attributes, @PathVariable("id") UUID id) {
		var check = systemService.changeStatus(id);
		attributes.addFlashAttribute("msg",check);
		return "redirect:/admin/panel/index";
	}
	


  
}
