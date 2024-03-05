package com.bookingtable.controllers.system;

import com.bookingtable.dtos.RateTypeDto;
import com.bookingtable.dtos.RoleDto;
import com.bookingtable.servicies.IRateService;
import com.bookingtable.servicies.IRateTypeService;
import com.bookingtable.servicies.IRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("staff/rateType" )
public class RateTypeController {
    @Autowired
    private IRateTypeService iRateTypeService;

    @GetMapping({"index"})
    public String index(Model model) {
        model.addAttribute("rateTypes", iRateTypeService.getAllRateTypes());
        return "staff/rateType/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        RateTypeDto rateTypeDto = new RateTypeDto();
        model.addAttribute("rateTypeDto", rateTypeDto);
        return "staff/rateType/create";
    }

    @PostMapping("create/save")
    public String createProcess(@Valid @ModelAttribute("rateTypeDto") RateTypeDto rateTypeDto,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "staff/rateType/create";
        }
        var response = iRateTypeService.createRateType(rateTypeDto);
        if(response.isStatus()) {
            return "redirect:/staff/rateType/index";
        }else {
            bindingResult.addError(new FieldError("rateTypeDto","type", response.getMessage().getType()));
            return "staff/rateType/create";
        }

    }
    @GetMapping("edit/{id}")
    public String update(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("rateTypeDto", iRateTypeService.getRateTypeById(id));
        return "staff/rateType/edit";
    }
    @PostMapping("updateProcess")
    public String updateProcess(@Valid @ModelAttribute("rateTypeDto") RateTypeDto rateTypeDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "staff/rateType/edit";
        }
        var response = iRateTypeService.updateRateType(rateTypeDto.getId(),rateTypeDto);
        if(response.isStatus()) {

            return "redirect:/staff/rateType/index";
        }else {
            bindingResult.addError(new FieldError("rateTypeDto","type", response.getMessage().getType()));
            return "staff/rateType/edit";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        var response = iRateTypeService.deleteRateType(id);
        if(response.isStatus()) {
            attributes.addFlashAttribute("msg", response);
            return "redirect:/staff/rateType/index";
        }else {
            attributes.addFlashAttribute("msg", response);
            return "redirect:/staff/rateType/index";
        }
    }
}
