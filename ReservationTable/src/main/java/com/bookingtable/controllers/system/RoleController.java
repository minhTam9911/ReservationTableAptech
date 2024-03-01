package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.IRoleService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("system/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@GetMapping({"index",""})
	public String index(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		return "system/role/index";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		RoleDto roleDto = new RoleDto();
		model.addAttribute("roleDto", roleDto);
		return "system/role/create";
	}
	
	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "system/role/create";
		}
		roleDto.setName(roleDto.getName().toUpperCase());
		var response = roleService.createRole(roleDto);
		if(response.isStatus()) {
			return "redirect:/system/role/index";
		}else {
			  
		       bindingResult.addError(new FieldError("roleDto","name", response.getMessage().getName()));
		       return "system/role/create";
		}
		
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("roleDto", roleService.getRoleById(id));
		return "system/role/edit";
	}
	@PostMapping("updateProcess")
	public String updateProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "system/role/edit";
		}
		roleDto.setName(roleDto.getName().toUpperCase());
		var response = roleService.updateRole(roleDto.getId(),roleDto);
		if(response.isStatus()) {
			
			return "redirect:/system/role/index";
		}else {
			 bindingResult.addError(new FieldError("roleDto","name", response.getMessage().getName()));
		       return "system/role/edit";
		}
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		var response = roleService.deleteRole(id);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/system/role/index";
		}else {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/system/role/index";
		}
	}
}
