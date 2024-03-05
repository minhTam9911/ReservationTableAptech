package com.bookingtable.controllers.system;

import com.bookingtable.dtos.CustomerDto;
import com.bookingtable.servicies.ICustomerService;
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

import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.dtos.SystemDto;
import com.bookingtable.servicies.IRoleService;
import com.bookingtable.servicies.ISystemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/panel/customer" )
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IRoleService roleService;

    private ResultResponse<CustomerDto> response = new ResultResponse<>();
    public CustomerController() {
        this.response = new ResultResponse<>(new CustomerDto());
    }

    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("data", iCustomerService.getAllCustomer());
        if(response.getMessage().getEmail() !=null) {
            if(response.isStatus()) {
                model.addAttribute("msg",true);
            }if(response.isStatus() == false) {
                model.addAttribute("msg",response.getMessage().getEmail());
            }
        }
        return "admin/panel/customer/index";
    }

    @GetMapping("create")
    public String create(Model model,RedirectAttributes attributes) {
        var customerDto = new CustomerDto();
        customerDto.setRoleList(roleService.getAllRoles());
        model.addAttribute("customerDto", customerDto);
        if(response.isStatus()) {
            model.addAttribute("msg",true);
            return "admin/panel/customer/create";
        }

        model.addAttribute("msg",response.getMessage().getEmail());
        return "admin/panel/customer/create";
    }

    @PostMapping("create/save")
    public String createProcess(@Valid @ModelAttribute("customerDto") CustomerDto customerDto ,
                                BindingResult bindingResult) {

        var roleData = roleService.getRoleById(customerDto.getRoleId());
        customerDto.setRoleDto(roleData);

        if(bindingResult.hasErrors()) {
            customerDto.setRoleList(roleService.getAllRoles());
            return "admin/panel/customer/create";
        }
        var response = iCustomerService.createCustomer(customerDto);
        if(response.isStatus()) {
            this.response.setStatus(true);
            return "redirect:/admin/panel/customer/index";
        }else {
            this.response.setMessage(new CustomerDto(response.getMessage().getEmail()));
            customerDto.setRoleList(roleService.getAllRoles());
            return "redirect:/admin/panel/customer/create";

        }

    }
    @GetMapping("update/{id}")
    public String update(Model model, @PathVariable("id") String id) {
        var customerDto = iCustomerService.getCustomerById(id);
        customerDto.setRoleId(customerDto.getRoleDto().getId());
        customerDto.setRoleList(roleService.getAllRoles());
        model.addAttribute("customerDto", customerDto);

        return "admin/panel/customer/edit";
    }
    @PostMapping("update/save")
    public String updateProcess(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "admin/panel/customer/edit";
        }
        var response = iCustomerService.updateCustomer(customerDto.getId(),customerDto);
        if(response.isStatus()) {
            this.response.setStatus(true);;
            return "redirect:/admin/panel/index";
        }else {
            this.response.setMessage(new CustomerDto(response.getMessage().getEmail()));
            customerDto.setRoleList(roleService.getAllRoles());
            return "admin/panel/customer/edit";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attributes) {
        var response = iCustomerService.deleteCustomer(id);
        if(response.isStatus()) {
            attributes.addFlashAttribute("msg", true);
            return "redirect:/admin/panel/index";
        }else {
            attributes.addFlashAttribute("msg", response.getMessage().getEmail());
            return "redirect:/admin/panel/index";
        }
    }
}
