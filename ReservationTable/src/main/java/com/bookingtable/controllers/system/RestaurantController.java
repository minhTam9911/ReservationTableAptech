package com.bookingtable.controllers.system;


import com.bookingtable.dtos.RestaurantDto;

import com.bookingtable.servicies.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RestaurantController {
    @Autowired
    private IRestaurantService iRestaurantService;

    @GetMapping("/restaurantController")
    public String getAllCustomers(Model model) {
        List<RestaurantDto> restaurants = iRestaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "restaurants-list";
    }
    // thêm create và update và delete
}
