package com.bookingtable.controllers.system;


import com.bookingtable.dtos.*;
import com.bookingtable.helpers.FileHelper;
import com.bookingtable.models.Image;
import com.bookingtable.servicies.ICategoryRestaurantService;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("partner/restaurant")
public class RestaurantController {
    @Autowired
    private IRestaurantService iRestaurantService;
    @Autowired
    private IImageService iImageService;
    @Autowired
    private ICategoryRestaurantService categoryRestaurantService;
	@Autowired
	private IDinnerTableService iDinnerTableService;
    private ResultResponse<Boolean> response = new ResultResponse<>(null);
    
    public RestaurantController() {
		super();
		this.response = new ResultResponse<>(null);
	}

	@GetMapping("index")
    public String getAllRestaurants(Model model, Principal principal) {
		List<RestaurantDto> restaurants = iRestaurantService.getAllRestaurantsForAgent(principal.getName());
		for (var restaurant : restaurants) {
			Set<ImageDto> images = iImageService.getImagesByRestaurantId(restaurant.getId());
			restaurant.setImagesDto(new ArrayList<>(images));
		}

        model.addAttribute("restaurants", restaurants);
        return "partner/restaurant/index";
    }

    @GetMapping("create")
    public String create(Model model) {
    
        RestaurantDto restaurantDto = new RestaurantDto();
        model.addAttribute("restaurantDto", restaurantDto);
        model.addAttribute("category", categoryRestaurantService.getList());
        if(response.isStatus()) {
			model.addAttribute("msg",true);
		}
        model.addAttribute("msg",false);
        return "partner/restaurant/create";
    }
    @PostMapping("create/save")
	public String createProcess(@Valid @ModelAttribute("restaurantDto") RestaurantDto restaurantDto ,
			BindingResult bindingResult,Model model,Principal principal, @RequestParam("images") MultipartFile[] images) {
	
		restaurantDto.setImagesDto(new ArrayList<ImageDto>());
		if(bindingResult.hasErrors()) {
			   model.addAttribute("category", categoryRestaurantService.getList());
			return "partner/restaurant/create";
		}
		var category = categoryRestaurantService.getById(restaurantDto.getCategoryId());
		restaurantDto.setCategoryRetaurantDto(category);
		var response = iRestaurantService.createRestaurant(restaurantDto,principal.getName());
		if(response.isStatus()) {
			this.response.setStatus(true);
			for(var i : images) {
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

	@GetMapping("edit/{id}")
	public String showEditRestaurantForm(@PathVariable("id") String id, ModelMap model,Principal principal) {
		RestaurantDto restaurantDto = iRestaurantService.getRestaurantById(id);
		Set<ImageDto> getExistingImages = iImageService.getImagesByRestaurantId(id);
		List<ImageDto> imageDtos = new ArrayList<>(getExistingImages);
		restaurantDto.setCreateBy(restaurantDto.getCreateBy());
		restaurantDto.setImagesDto(imageDtos);
		restaurantDto.setId(id);
		model.addAttribute("imageDtos", imageDtos);
		var categoryId = restaurantDto.getCategoryRetaurantDto().getId();
		restaurantDto.setCategoryId(categoryId);
		   model.addAttribute("category", categoryRestaurantService.getList());
		model.addAttribute("restaurantDto", restaurantDto);
		if(response.isStatus()) {
			model.addAttribute("msg",true);
			return "partner/restaurant/edit";
		}
		return "partner/restaurant/edit";
	}

	@PostMapping("edit/save")
	public String editRestaurant(@ModelAttribute("restaurantDto") RestaurantDto restaurantDto,
								  @RequestParam("images") MultipartFile[] images,
								  RedirectAttributes redirectAttributes,
								  BindingResult bindingResult,
								 Principal principal,
								 Model model
	) {
		if(bindingResult.hasErrors()) {
			   model.addAttribute("category", categoryRestaurantService.getList());
			return "partner/restaurant/edit";
		}
		List<ImageDto> existingImages = new ArrayList<>(iImageService.getImagesByRestaurantId(restaurantDto.getId()));
		restaurantDto.setImagesDto(existingImages);
		var category = categoryRestaurantService.getById(restaurantDto.getCategoryId());
		restaurantDto.setCategoryRetaurantDto(category);
		var response = iRestaurantService.updateRestaurant(restaurantDto.getId(), restaurantDto, principal.getName());
		Set<String> imagePaths = new HashSet<>(); // Tạo một tập hợp để lưu trữ các đường dẫn hình ảnh mới

// Lặp qua danh sách các hình ảnh mới
		for (MultipartFile image : images) {
			ImageDto imageDto = new ImageDto();
			imageDto.setPath(FileHelper.uploadRestaurant(image));
			imageDto.setRestaurantDto(restaurantDto);
			imagePaths.add(imageDto.getPath()); // Thêm đường dẫn hình ảnh mới vào tập hợp

			// Kiểm tra xem hình ảnh đã tồn tại trong danh sách cũ hay chưa
			boolean isExisting = false;
			for (ImageDto existingImage : existingImages) {
				if (existingImage.getPath().equals(imageDto.getPath())) {
					isExisting = true;
					break;
				}
			}

			// Nếu hình ảnh mới không tồn tại trong danh sách cũ, thêm mới vào
			if (!isExisting) {
				iImageService.createImage(imageDto);
			}
		}

// Lặp qua danh sách các hình ảnh cũ và kiểm tra xem có hình ảnh nào cần xóa không
		for (ImageDto existingImage : existingImages) {
			if (!imagePaths.contains(existingImage.getPath())) {
				// Nếu đường dẫn hình ảnh cũ không tồn tại trong tập hợp các đường dẫn hình ảnh mới
				// thì xóa hình ảnh cũ
				iImageService.deleteImage(existingImage.getId());
				FileHelper.deleteRestaurantImage(existingImage.getPath());
			}
		}

		
		if(response.isStatus()) {
			this.response.setStatus(true);
			return "redirect:/partner/restaurant/index";
		}else {
			return "redirect:/partner/restaurant/edit";

		}
	}
	@GetMapping("/delete/{id}")
	public String deleteRestaurant(@PathVariable String id,Principal principal) {
		var existImages = iImageService.getImagesByRestaurantId(id);
		var existDinnerTables = iDinnerTableService.getAllDinnerTablesForRestaurant(id);

		for(var existImage :existImages) {
			FileHelper.deleteRestaurantImage(existImage.getPath());
			iImageService.deleteImage(existImage.getId());
		}
		for(var existDinnerTable :existDinnerTables) {
			iDinnerTableService.deleteDinnerTable(existDinnerTable.getId());
		}
		iRestaurantService.deleteRestaurant(id,principal.getName());
		return "redirect:/partner/restaurant/index";
	}
}
