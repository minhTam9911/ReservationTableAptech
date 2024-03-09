package com.bookingtable.controllers.system;

import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.implement.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("staff/dinnerTableType" )
public class DinnerTableTypeController {
    @Autowired
    private RestaurantService iRestaurantService;
    @Autowired
    private IDinnerTableTypeService idinnerTableTypeService;

    @GetMapping({ "index", "", "/" })
    public String getAllDinnerTableTypes(Model model) {
        List<DinnerTableTypeDto> dinnerTableTypes = idinnerTableTypeService.getAllDinnerTablesType();

        model.addAttribute("dinnerTableTypes",dinnerTableTypes);

        return "staff/dinnerTableType/index";
    }
    @GetMapping("create")
    public String create(Model model) {
        DinnerTableTypeDto dinnerTableTypeDto = new DinnerTableTypeDto();
        model.addAttribute("dinnerTableTypeDto", dinnerTableTypeDto);
        return "staff/dinnerTableType/create";
    }

    @PostMapping("/create")
    public String createDinnerTableType(@Valid @ModelAttribute("dinnerTableTypeDto") DinnerTableTypeDto dinnerTableTypeDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "staff/dinnerTableType/create";
        }
        var response = idinnerTableTypeService.createDinnerTableType(dinnerTableTypeDto);
        if (response.isStatus()) {
            return "redirect:/staff/dinnerTableType/index";
        } else {
            bindingResult.addError(
                    new FieldError("dinnerTableTypeDto", "type", response.getMessage().getType())

            );
            return "staff/dinnerTableType/create";
        }
    }

    @GetMapping("edit/{id}")
    public String updateShowingForm(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("dinnerTableTypeDto", idinnerTableTypeService.getDinnerTableTypeById(id));
        return "staff/dinnerTableType/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateDinnerTableType(@Valid @ModelAttribute("dinnerTableTypeDto") DinnerTableTypeDto dinnerTableTypeDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "staff/dinnerTableType/edit";
        }
        var response = idinnerTableTypeService.updateDinnerTableType(dinnerTableTypeDto.getId(),dinnerTableTypeDto);
        if(response.isStatus()) {
            return "redirect:/staff/dinnerTableType/index";
        }else {
            bindingResult.addError(new FieldError("dinnerTableTypeDto","name", response.getMessage().getType()));
            return "staff/dinnerTableType/edit";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        var response = idinnerTableTypeService.deleteDinnerTableType(id);
        if(response.isStatus()) {
            attributes.addFlashAttribute("msg", response);
            return "redirect:/staff/dinnerTableType/index";
        }else {
            attributes.addFlashAttribute("msg", response);
            return "redirect:/staff/dinnerTableType/index";
        }
    }
}
