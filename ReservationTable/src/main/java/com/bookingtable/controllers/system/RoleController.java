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
	private ResultResponse<String> result = new ResultResponse<>(false, 0, "");

	@GetMapping({ "index" })
	public String index(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		return "admin/panel/role/index";
	}

	@GetMapping("create")
	public String create(Model model) {
		RoleDto roleDto = new RoleDto();
		model.addAttribute("roleDto", roleDto);
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
		return "admin/panel/role/create";
	}

	@PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("roleDto") RoleDto roleDto,
			RedirectAttributes redirectAttributes, BindingResult bindingResult,Model model) {
		if (bindingResult.hasErrors()) {
			result.setOption(2);
			result.setMessage("Form valid");
			model.addAttribute("msg", result);
			result = new ResultResponse<>(false,0,"");
			return "admin/panel/role/create";
		}
		roleDto.setName(roleDto.getName().toUpperCase());
		result = roleService.createRole(roleDto);
		if (result.getOption() == 1) {
			return "redirect:/admin/panel/role/index";
		}
//		if (result.getOption() == 2) {
//		}
		return "admin/panel/role/create";
	}

	@GetMapping("update/{id}")
	public String update(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("msg", result);
		result = new ResultResponse<>(false,0,"");
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
		result = roleService.updateRole(roleDto.getId(), roleDto);
		if (result.getOption() == 1) {
			return "redirect:/admin/panel/role/index";
		}
		if (result.getOption() == 2) {
			return "admin/panel/staff/edit";
		}
		return "admin/panel/role/edit";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		result = roleService.deleteRole(id);
		if (result.getOption()==1){
			result.setOption(1);
			result.setMessage("Process Success");
		}
		if (result.getOption()==2){
			result.setOption(2);
			result.setMessage("Process Failure");
		}
		return "redirect:/admin/panel/role/index";

	}
}
