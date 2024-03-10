package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;
import com.bookingtable.servicies.implement.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("customer/dinnerTables")
public class DinnerTablesController {
    @Autowired
    private IDinnerTableService iDinnerTableService;
    @Autowired
    private IImageService iImageService;
    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {
        String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        model.addAttribute("requestURI", requestURI);
        var dinnerTables = iDinnerTableService.getAllDinnerTables(); // Assuming this returns a copy of the list you can modify, or simply objects that you're updating.
        for (DinnerTableDto dinnerTable : dinnerTables) {
            Set<ImageDto> images = iImageService.getImagesByDinnerTableId(dinnerTable.getId());
            dinnerTable.setImagesDto(new ArrayList<>(images));
        }
        model.addAttribute("dinnerTables", dinnerTables);
        return "customer/dinnerTables/index";
    }


}