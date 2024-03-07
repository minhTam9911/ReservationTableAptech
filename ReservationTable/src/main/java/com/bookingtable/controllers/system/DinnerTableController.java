package com.bookingtable.controllers.system;

import com.bookingtable.dtos.*;
import com.bookingtable.helpers.FileHelper;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.IRestaurantService;
import com.bookingtable.servicies.implement.ImageService;
import com.bookingtable.servicies.implement.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("partner/dinnerTable" )
public class DinnerTableController {
    @Autowired
    private IDinnerTableService iDinnerTableService;

    @Autowired
    private IDinnerTableTypeService idinnerTableTypeService;
    @Autowired
    private IRestaurantService iRestaurantService;

    @Autowired
    private ImageService imageService;


    private ResultResponse<DinnerTableDto> response = new ResultResponse<>();
    public DinnerTableController() {
        this.response = new ResultResponse<>(new DinnerTableDto());
    }
    @Autowired
    private RestaurantService restaurantService;
    @RequestMapping({ "index", "", "/" })
    public String getAllDinnerTables(Model model,Principal principal) {
        List<RestaurantDto> restaurants = iRestaurantService.getAllRestaurantsForAgent(principal.getName());
        for (RestaurantDto restaurant : restaurants){
            List<DinnerTableDto> dinnerTables = iDinnerTableService.getAllDinnerTablesForRestaurant(restaurant.getId());
            model.addAttribute("dinnerTables", dinnerTables);
            for (DinnerTableDto dinnerTable : dinnerTables) {
                Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
                dinnerTable.setImagesDto(new ArrayList<>(images));
            }
        }
        return "partner/dinnerTable/index";
    }

    @GetMapping("/partner-details/{id}")
    public String getDinnerTableById(@PathVariable Integer id, Model model) {
        DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
        model.addAttribute("dinnerTable", dinnerTableDto);
        return "partner/details";
    }

    @GetMapping("create")
    public String showDinnerTableCreateForm(Model model) {
        var dinnerTableDto = new DinnerTableDto();

        List<DinnerTableTypeDto> dinnerTableTypes = idinnerTableTypeService.getAllDinnerTablesType();
        dinnerTableDto.setDinnerTableTypeList(dinnerTableTypes);
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        dinnerTableDto.setRestaurantList(restaurants);
        model.addAttribute("dinnerTableDto", dinnerTableDto);
        if(response.isStatus()) {
            model.addAttribute("msg",true);
            return "partner/dinnerTable/create";
        }
        return "partner/dinnerTable/create";
    }

    @PostMapping("create/save")
    public String createDinnerTable(@Valid @ModelAttribute("dinnerTableDto") DinnerTableDto dinnerTableDto,
                                    @RequestParam("images") MultipartFile[] images,
                                    BindingResult bindingResult) {
        List<ImageDto> imageDtos = new ArrayList<>();
        dinnerTableDto.setImagesDto(imageDtos);
        var dinnerTableTypeDto = idinnerTableTypeService.getDinnerTableTypeById(dinnerTableDto.getDinnerTableTypeDtoId());
        dinnerTableDto.setDinnerTableTypeDto(dinnerTableTypeDto);
        var restaurantDto = iRestaurantService.getRestaurantById(dinnerTableDto.getRestaurantDtoId());
        dinnerTableDto.setRestaurantDto(restaurantDto);
        var response = iDinnerTableService.createDinnerTable(dinnerTableDto);
        for (MultipartFile image : images) {
            ImageDto imageDto = new ImageDto();
            imageDto.setPath(FileHelper.uploadDinnerTable(image));
            imageDto.setDinnerTableDto(response.getMessage());
            imageDto.setRestaurantDto(restaurantDto);
            imageService.createImage(imageDto);
        }
        if(bindingResult.hasErrors()) {
            dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
            dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
            return "partner/dinnerTable/create";
        }
        if(response.isStatus()) {
            this.response.setStatus(true);
            return "redirect:/partner/dinnerTable/index";
        }else {
            dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
            dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
            return "redirect:/partner/dinnerTable/create";

        }
    }

    @GetMapping("edit/{id}")
    public String showEditDinnerTableForm(@PathVariable("id") Integer id, ModelMap model) {
        DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
        dinnerTableDto.setId(id);
        dinnerTableDto.setDinnerTableTypeDtoId(dinnerTableDto.getDinnerTableTypeDto().getId());
        dinnerTableDto.setRestaurantDtoId(dinnerTableDto.getRestaurantDto().getId());

        model.addAttribute("dinnerTableDto", dinnerTableDto);

        List<DinnerTableTypeDto> dinnerTableTypes = idinnerTableTypeService.getAllDinnerTablesType();
        dinnerTableDto.setDinnerTableTypeList(dinnerTableTypes);

        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        dinnerTableDto.setRestaurantList(restaurants);

        if(response.isStatus()) {
            model.addAttribute("msg",true);
            return "partner/dinnerTable/edit";
        }
        return "partner/dinnerTable/edit";
    }

    @PostMapping("edit/save")
    public String editDinnerTable(@ModelAttribute("dinnerTableDto") DinnerTableDto dinnerTableDto,
                                  @RequestParam("images") MultipartFile[] images,
                                  RedirectAttributes redirectAttributes,
                                  BindingResult bindingResult
    ) {

        List<ImageDto> imageDtos = new ArrayList<>();
        var dinnerTableTypeDto = idinnerTableTypeService.getDinnerTableTypeById(dinnerTableDto.getDinnerTableTypeDtoId());
        dinnerTableDto.setDinnerTableTypeDto(dinnerTableTypeDto);
        var restaurantDto = iRestaurantService.getRestaurantById(dinnerTableDto.getRestaurantDtoId());
        dinnerTableDto.setRestaurantDto(restaurantDto);
        var response = iDinnerTableService.updateDinnerTable(dinnerTableDto.getId(),dinnerTableDto);
//        for (MultipartFile image : images) {
//            ImageDto imageDto = new ImageDto();
//            imageDto.setPath(FileHelper.uploadDinnerTable(image));
//            imageDto.setDinnerTableDto(response.getMessage());
//            imageDto.setRestaurantDto(restaurantDto);
//            imageService.createImage(imageDto);
//        }
        if(bindingResult.hasErrors()) {
            dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
            dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
            return "partner/dinnerTable/edit";
        }
        if(response.isStatus()) {
            this.response.setStatus(true);
            return "redirect:/partner/dinnerTable/index";
        }else {
            dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
            dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
            return "redirect:/partner/dinnerTable/edit";

        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDinnerTable(@PathVariable Integer id) {
        iDinnerTableService.deleteDinnerTable(id);
        return "redirect:/partner/dinnerTable";
    }
}
