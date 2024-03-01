package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("system/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@GetMapping({"index","","/"})
	public String index(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		return "system/role";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		model.addAttribute("roleDto", new RoleDto());
		return "system/role/create";
	}
	
	@PostMapping("create")
	public String createProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto,RedirectAttributes attributes, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "system/role/create";
		}
		var response = roleService.createRole(roleDto);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/system/role/create";
		}else {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/system/role/create";
		}
	}
	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("roleDto", roleService.getRoleById(id));
		return "system/role/update";
	}
	@PostMapping("updateProcess")
	public String updateProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto, RedirectAttributes attributes, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "system/role/update";
		}
		var response = roleService.updateRole(roleDto.getId(),roleDto);
		if(response.isStatus()) {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/system/role/update";
		}else {
			attributes.addFlashAttribute("msg", response);
			return "redirect:/system/role/update";
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
