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

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.IRoleService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/panel/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	private ResultResponse<String> response = new ResultResponse<>(false, 0, "");

	@GetMapping({ "index" })
	public String index(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		return "admin/panel/role/index";
	}

	@GetMapping("create")
	public String create(Model model) {
		RoleDto roleDto = new RoleDto();
		model.addAttribute("roleDto", roleDto);
		return "admin/panel/role/create";
	}

	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/panel/role/create";
		}
		roleDto.setName(roleDto.getName().toUpperCase());
		response = roleService.createRole(roleDto);
		if (response.getOption() == 1) {
			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/admin/panel/role/index";
		}
		if (response.getOption() == 2) {

			redirectAttributes.addFlashAttribute("msg", response);

		}
		return "admin/panel/role/create";
	}

	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("roleDto", roleService.getRoleById(id));
		return "admin/panel/role/edit";
	}

	@PostMapping("updateProcess")
	public String updateProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/panel/role/edit";
		}
		roleDto.setName(roleDto.getName().toUpperCase());
		response = roleService.updateRole(roleDto.getId(), roleDto);
		if (response.getOption() == 1) {
			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/admin/panel/role/index";
		}
		if (response.getOption() == 2) {
			redirectAttributes.addFlashAttribute("msg", response);
		}
		return "admin/panel/role/edit";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		response = roleService.deleteRole(id);

		attributes.addFlashAttribute("msg", response);
		return "redirect:/admin/panel/role/index";

	}
}
