package com.bookingtable.controllers.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("customer/restaurant")
public class CustomerRestaurantController {

	@Autowired
	private IRestaurantService restaurantService;
	@Autowired 
	private IImageService imageService;
	
	
	@GetMapping({"index","/",""})
	public String index(Model model) {
		String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
		model.addAttribute("requestURI", requestURI);
		var restaurants = restaurantService.getAllRestaurants();
		List<RestaurantDto> data = new ArrayList<>();
		for(var i : restaurants) {
			var restaurant = new RestaurantDto();
			restaurant =  i;
			for(int j = 0; j<=2;j++) {
				ImageDto image = new ImageDto();
				var images = imageService.getImagesByRestaurantId(restaurant.getId()).stream().collect(Collectors.toList());
				image = images.get(j);
				restaurant.setImageSrc(image.getPath());
			}
			data.add(restaurant);
		}
		model.addAttribute("data", data);
		return "customer/restaurant/index";
	}
}
