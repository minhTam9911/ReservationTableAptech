package com.bookingtable.controllers.system;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("system/dinnerTable" )
public class DinnerTableController {
    @Autowired
    private IDinnerTableService iDinnerTableService;
    @Autowired
    private IPaginationService iPaginationService;

    @GetMapping({ "index", "", "/" })
    public String getAllCustomers(@RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  Model model) {
        List<DinnerTableDto> dinnerTables = iDinnerTableService.getAllDinnerTables();
        int[] pageSizes = {5, 10, 20};
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<DinnerTableDto> dinnerTablesPage = iPaginationService.findPaginated(dinnerTables, PageRequest.of(currentPage - 1, pageSize));
        int previousPage = Math.max(1, currentPage - 1);
        int nextPage = Math.max(1, currentPage + 1);
        int totalPages = dinnerTablesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("dinnerTablePages", dinnerTablesPage);
        model.addAttribute("pageSizes", pageSizes);

        model.addAttribute("pageSize", pageSize);

        return "system/dinnerTable/index";
    }
    @GetMapping("/dinnerTable-details/{id}")
    public String getDinnerTableById(@PathVariable Integer id, Model model) {
        DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
        model.addAttribute("dinnerTable", dinnerTableDto);
        return "system/dinnerTable/details";
    }

    @GetMapping("/create")
    public String showDinnerTableCreateForm(Model model) {
        DinnerTableDto dinnerTableDto = new DinnerTableDto();
        DinnerTableTypeDto dinnerTableTypeDto = new DinnerTableTypeDto();
        ImageDto imageDto = new ImageDto();
        model.addAttribute("dinnerTableDto", dinnerTableDto);
        return "system/dinnerTable/create";
    }

    @PostMapping("/create")
    public String createDinnerTable(@ModelAttribute("dinnerTableDto") DinnerTableDto dinnerTableDto, RedirectAttributes redirectAttributes) {
        iDinnerTableService.createDinnerTable(dinnerTableDto);
        redirectAttributes.addAttribute("Create success");
        return "redirect:/system/dinnerTable";
    }

    @GetMapping("/edit/{id}")
    public String showEditDinnerTableForm(@PathVariable("id") Integer id, ModelMap modelMap) {
        DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
        modelMap.addAttribute("dinnerTableDto", dinnerTableDto);
        return "system/dinnerTable/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDinnerTable(@PathVariable("id") Integer id, @ModelAttribute("dinnerTableDto") DinnerTableDto updatedDinnerTable) {
        DinnerTableDto existingDinnerTable = iDinnerTableService.getDinnerTableById(id);
        if (existingDinnerTable != null) {
            existingDinnerTable.setStatus(updatedDinnerTable.getStatus());
            existingDinnerTable.setQuantity(updatedDinnerTable.getQuantity());
            existingDinnerTable.setDinnerTableTypeDto(updatedDinnerTable.getDinnerTableTypeDto());
            existingDinnerTable.setRestaurantDto(updatedDinnerTable.getRestaurantDto());
            existingDinnerTable.setImagesDto(updatedDinnerTable.getImagesDto());

            iDinnerTableService.updateDinnerTable(existingDinnerTable);
        }
        return "redirect:/system/dinnerTable";
    }

    @GetMapping("/delete/{id}")
    public String deleteDinnerTable(@PathVariable Integer id) {
        iDinnerTableService.deleteDinnerTable(id);
        return "redirect:/system/dinnerTable";
    }
}
