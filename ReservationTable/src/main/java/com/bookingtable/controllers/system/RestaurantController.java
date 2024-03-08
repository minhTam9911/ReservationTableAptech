package com.bookingtable.controllers.system;


import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.ReceptionistDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.helpers.FileHelper;
import com.bookingtable.models.Image;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("partner/restaurant")
public class RestaurantController {
    @Autowired
    private IRestaurantService iRestaurantService;
    @Autowired
    private IImageService iImageService;
    private ResultResponse<Boolean> response = new ResultResponse<>(null);
    
    public RestaurantController() {
		super();
		this.response = new ResultResponse<>(null);
	}

	@GetMapping("index")
    public String getAllRestaurants(Model model, Principal principal) {
        List<RestaurantDto> restaurants = iRestaurantService.getAllRestaurantsForAgent(principal.getName());
        model.addAttribute("restaurants", restaurants);
        return "partner/restaurant/index";
    }

    @GetMapping("create")
    public String create(Model model) {
    
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurantDto", restaurantDto);
        
        if(response.isStatus()) {
			model.addAttribute("msg",true);
		}
        model.addAttribute("msg",false);
        return "partner/restaurant/create";
    }
    @PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("restaurantDto") RestaurantDto restaurantDto ,
			BindingResult bindingResult,Principal principal, @RequestParam("file") MultipartFile[] file) {
	
		restaurantDto.setImagesDto(new ArrayList<ImageDto>());
		if(bindingResult.hasErrors()) {
			return "partner/restaurant/create";
		}
		var response = iRestaurantService.createRestaurant(restaurantDto,principal.getName());
		if(response.isStatus()) {
			this.response.setStatus(true);
			for(var i : file) {
				var image = new ImageDto();
	    		var fileName =  FileHelper.uploadRestaurant(i);
	    		image.setPath(fileName);
	    		image.setRestaurantDto(iRestaurantService.getRestaurantById(response.getMessage().getId(), principal.getName()));
	    		iImageService.createImage(image);
			}
			return "redirect:/partner/restaurant/index";
		}else {
			this.response.setStatus(false);
			return "redirect:/partner/restaurant/create";
			
		}
		
	}
}
