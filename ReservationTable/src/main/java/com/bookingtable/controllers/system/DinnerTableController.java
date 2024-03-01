package com.bookingtable.controllers.system;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.IRestaurantService;
import com.bookingtable.servicies.implement.ImageService;
import com.bookingtable.servicies.implement.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("system/dinnerTable" )
public class DinnerTableController {
    @Autowired
    private IDinnerTableService iDinnerTableService;

    @Autowired
    private IDinnerTableTypeService dinnerTableTypeService;
    @Autowired
    private IRestaurantService iRestaurantService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RestaurantService restaurantService;
    @GetMapping({ "index", "", "/" })
    public String getAllCustomers(Model model) {
        List<DinnerTableDto> dinnerTables = iDinnerTableService.getAllDinnerTables();
        model.addAttribute("dinnerTables",dinnerTables);

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
        model.addAttribute("dinnerTableDto", dinnerTableDto);

        List<DinnerTableTypeDto> dinnerTableTypes = dinnerTableTypeService.getAllDinnerTablesType();
        model.addAttribute("dinnerTableTypes", dinnerTableTypes);

        // Lấy danh sách hình ảnh từ service và chuyển vào model
        List<ImageDto> images = imageService.getAllImages();
        model.addAttribute("images", images);

        // Lấy danh sách nhà hàng từ cơ sở dữ liệu và chuyển vào model
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);

        return "system/dinnerTable/create";
    }

    @PostMapping("/create")
    public String createDinnerTable(@ModelAttribute("dinnerTableDto") DinnerTableDto dinnerTableDto,
                                    @ModelAttribute("dinnerTableType") Integer dinnerTableTypeId,
                                    @ModelAttribute("restaurant") String restaurantId,
                                    RedirectAttributes redirectAttributes) {
        try {
            // Gán đối tượng DinnerTableTypeDto và RestaurantDto
            DinnerTableTypeDto dinnerTableType = dinnerTableTypeService.getDinnerTableTypeById(dinnerTableTypeId);
            RestaurantDto restaurantDto = iRestaurantService.getRestaurantById(restaurantId);
            dinnerTableDto.setDinnerTableTypeDto(dinnerTableType);
            dinnerTableDto.setRestaurantDto(restaurantDto);

            // Tạo bàn ăn
            iDinnerTableService.createDinnerTable(dinnerTableDto);

            redirectAttributes.addFlashAttribute("message", "Dinner table created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create dinner table: " + e.getMessage());
        }
        return "redirect:/system/dinnerTable";
    }



    @GetMapping("/edit/{id}")
    public String showEditDinnerTableForm(@PathVariable("id") Integer id, ModelMap model) {
        List<DinnerTableTypeDto> dinnerTableTypes = dinnerTableTypeService.getAllDinnerTablesType();
        model.addAttribute("dinnerTableTypes", dinnerTableTypes);

        // Lấy danh sách nhà hàng từ cơ sở dữ liệu và chuyển vào model
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        // Lấy danh sách hình ảnh từ service và chuyển vào model
        List<ImageDto> images = imageService.getAllImages();
        model.addAttribute("images", images);

        DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
        model.addAttribute("dinnerTableDto", dinnerTableDto);
        return "system/dinnerTable/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDinnerTable(@PathVariable("id") Integer id,
                                  @ModelAttribute("dinnerTableDto") DinnerTableDto updatedDinnerTable,
                                  @ModelAttribute("dinnerTableType") Integer dinnerTableTypeId,
                                  @ModelAttribute("restaurant") String restaurantId) {

        DinnerTableDto existingDinnerTable = iDinnerTableService.getDinnerTableById(id);
        DinnerTableTypeDto dinnerTableType = dinnerTableTypeService.getDinnerTableTypeById(dinnerTableTypeId);
        RestaurantDto restaurantDto = iRestaurantService.getRestaurantById(restaurantId);
        updatedDinnerTable.setDinnerTableTypeDto(dinnerTableType);
        updatedDinnerTable.setRestaurantDto(restaurantDto);

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
