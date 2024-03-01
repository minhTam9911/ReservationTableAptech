package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.IPermissionService;
import com.bookingtable.servicies.IRoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("system/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permissionService;
	
	
	@GetMapping({"index","","/"})
	public String index(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		return "system/role";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		model.addAttribute("roleDto", new RoleDto());
		model.addAttribute("permissions", permissionService.getAllPermission());
		return "system/role/create";
	}
	
	@PostMapping("create")
	public String createProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto,RedirectAttributes attributes, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "system/role/create";
		}
		if(roleService.createRole(roleDto)) {
			return "redirect:/system/role/index";
		}else {
			attributes.addFlashAttribute("", attributes)
		}
	}
}
