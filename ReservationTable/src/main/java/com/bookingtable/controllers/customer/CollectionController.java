package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.util.ArrayList;

import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.servicies.ICollectionService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("customer/collection")
public class CollectionController {

	@Autowired
	private ICollectionService collectionService;
	@Autowired
	private IRestaurantService restaurantService;
	@Autowired
	private IImageService imageService;
	@GetMapping("index")
	public String index(Model model, Principal principal) {
		if(principal !=null) {
			var collections = collectionService.getByCustomer(principal.getName());
			var restaurants = new ArrayList<RestaurantDto>(); // Tạo danh sách để chứa tất cả các nhà hàng

			for (var collection : collections){
				var restaurant = restaurantService.getRestaurantById(collection.getRestaurant().getId());
				var imageList = new ArrayList<ImageDto>();
				var images = imageService.getImagesByRestaurantId(collection.getRestaurant().getId());
				for (var i: images){
					imageList.add(i);
					restaurant.setImagesDto(imageList);
				}
				restaurants.add(restaurant); // Thêm nhà hàng vào danh sách
			}
			model.addAttribute("restaurants",restaurants);
			return "customer/collection/index";
		}
		return "account/accessDined";
	}

	@GetMapping("delete/{id}")
	public String delete(Model model,@PathVariable("id") Integer id) {
			collectionService.delete(id);
			return "redirect:/customer/collection/index";
	}
	
	@GetMapping("remove/{id}")
	public String remove(Model model,@PathVariable("id") String id, Principal principal) {
		var collections = collectionService.getByCustomer(principal.getName());
		for(var i : collections) {
			if(i.getRestaurant().getId().equals(id)) {
				collectionService.delete(i.getId());
			}
		}
			
			return "redirect:/customer/collection/index";
	}
	
}
