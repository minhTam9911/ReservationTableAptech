package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.RestaurantDto;
import com.bookingtable.servicies.ICategoryRestaurantService;
import com.bookingtable.servicies.ICollectionService;
import com.bookingtable.servicies.ICustomerService;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;

@Controller
@RequestMapping("customer/category")
public class CategoryController {

	@Autowired
	private IDinnerTableTypeService dinnerTableTypeService;
	@Autowired
	private ICategoryRestaurantService categoryRestaurantService;
	@Autowired
	private IRestaurantService restaurantService;
	@Autowired
	private IDinnerTableService dinnerTableService;
	@Autowired
	private IImageService iImageService;
	@Autowired
	private ICustomerService iCustomerService;
	@Autowired
	private ICollectionService collectionService;
	
	
	@GetMapping("restaurant")
	public String indexRestaurant(Model model) {
		model.addAttribute("data", categoryRestaurantService.getList());
		return "customer/category/restaurant";
	}
	
	@GetMapping("dinnerTable")
	public String indexDinnerTable(Model model) {
		model.addAttribute("data", dinnerTableTypeService.getAllDinnerTablesType());
		return "customer/category/dinnerTable";
	}
	@GetMapping("restaurant/detail/{id}")
	public String detailRestaurant(Model model,@PathVariable("id") Integer id,Principal principal) {
		var restaurants =  restaurantService.getAllCategory(id);
		var list = new ArrayList<RestaurantDto>();
		 var lists = new ArrayList<RestaurantDto>();
	        for(var i : restaurants) {
	        	if(i.isActive()) {
	        		list.add(i);
	        	}
	        }
		  for (var i : list) {
	            var restaurant = new RestaurantDto();
	            restaurant = i;
	            if (principal != null) {
	                var customer = iCustomerService.getCustomerByEmail(principal.getName());

	                var collection = collectionService.findByCustomerAndRestaurant(customer.getId(), i.getId());

	                if (collection != null && collection.isStatus()) {
	                    i.setCollectionStatus(true); // Collection tồn tại và có trạng thái true
	                } else {
	                    i.setCollectionStatus(false); // Collection không tồn tại hoặc có trạng thái false
	                }
	            }
	            for (int j = 0; j <= 2; j++) {
	                ImageDto image = new ImageDto();
	                var images = iImageService.getImagesByRestaurantId(restaurant.getId()).stream().collect(Collectors.toList());
	                for(var x : images) {
	                	if(x.getDinnerTableDto() == null) {
	                		image = x;
	                        restaurant.setImageSrc(image.getPath());
	                	}
	                }
	                
	            }
	            lists.add(restaurant);
		  }
		  
		model.addAttribute("data",lists);
		return "customer/category/detail-restaurant";
	}
	
	@GetMapping("dinnerTable/detail/{id}")
	public String detailDinnerTable(Model model,@PathVariable("id") Integer id) {
		var dinnerTables = dinnerTableService.getAllCategory(id);
		 var list = new ArrayList<DinnerTableDto>();
	        for(var i : dinnerTables) {
	        	if(i.getRestaurantDto().isActive()) {
	        		list.add(i);
	        	}
	        }
		 for (DinnerTableDto dinnerTable : list) {
	            Set<ImageDto> images = iImageService.getImagesByDinnerTableId(dinnerTable.getId());
	            dinnerTable.setImagesDto(new ArrayList<>(images));
	        }
		model.addAttribute("data", list);
		return "customer/category/detail-dinnerTable";
	}
	
}
