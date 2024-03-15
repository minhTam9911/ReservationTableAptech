package com.bookingtable.controllers.system;

import com.bookingtable.dtos.*;
import com.bookingtable.helpers.FileHelper;
import com.bookingtable.models.Image;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.IImageService;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("partner/dinnerTable")
public class DinnerTableController {
	@Autowired
	private IDinnerTableService iDinnerTableService;

	@Autowired
	private IDinnerTableTypeService idinnerTableTypeService;
	@Autowired
	private IRestaurantService iRestaurantService;

	@Autowired
	private IImageService imageService;

	private ResultResponse<String> response = new ResultResponse<>(false, 0, "");
	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping({ "index", "", "/" })
	public String getAllDinnerTables(Model model, Principal principal) {
		List<RestaurantDto> restaurants = iRestaurantService.getAllRestaurantsForAgent(principal.getName());
		List<DinnerTableDto> dinnerTables = new ArrayList<>();

		for (var restaurant : restaurants) {
			List<DinnerTableDto> dinnerTableDtos = iDinnerTableService
					.getAllDinnerTablesForRestaurant(restaurant.getId());
			for (DinnerTableDto dinnerTable : dinnerTableDtos) {
				dinnerTables.add(dinnerTable);
				Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
				dinnerTable.setImagesDto(new ArrayList<>(images));
			}
		}
		model.addAttribute("dinnerTables", dinnerTables);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/dinnerTable/index";
	}

	@GetMapping("detail/{id}")
	public String getDinnerTableById(@PathVariable Integer id, Model model) {
		DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
		model.addAttribute("dinnerTable", dinnerTableDto);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/dinnerTable/details";
	}

	@GetMapping("create")
	public String showDinnerTableCreateForm(Model model, Principal principal) {
		var dinnerTableDto = new DinnerTableDto();

		List<DinnerTableTypeDto> dinnerTableTypes = idinnerTableTypeService.getAllDinnerTablesType();
		dinnerTableDto.setDinnerTableTypeList(dinnerTableTypes);
		List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsForAgent(principal.getName());
		dinnerTableDto.setRestaurantList(restaurants);
		model.addAttribute("dinnerTableDto", dinnerTableDto);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/dinnerTable/create";
	}

	@PostMapping("create/save")
	public String createDinnerTable(@Valid @ModelAttribute("dinnerTableDto") DinnerTableDto dinnerTableDto,
			BindingResult bindingResult, @RequestParam("images") MultipartFile[] images, Model model) {
		if (bindingResult.hasErrors()) {
			dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
			dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
			model.addAttribute("msg", response);
			response = new ResultResponse<>(false, 0, "");
			return "partner/dinnerTable/create";
		}
		dinnerTableDto.setCurrentQuantity(dinnerTableDto.getQuantity());
		List<ImageDto> imageDtos = new ArrayList<>();
		dinnerTableDto.setImagesDto(imageDtos);

		var dinnerTableTypeDto = idinnerTableTypeService
				.getDinnerTableTypeById(dinnerTableDto.getDinnerTableTypeDtoId());
		dinnerTableDto.setDinnerTableTypeDto(dinnerTableTypeDto);
		var restaurantDto = iRestaurantService.getRestaurantById(dinnerTableDto.getRestaurantDtoId());
		dinnerTableDto.setRestaurantDto(restaurantDto);

		response = iDinnerTableService.createDinnerTable(dinnerTableDto);

		if (response.getOption() == 1) {
			for (MultipartFile image : images) {
				ImageDto imageDto = new ImageDto();
				imageDto.setPath(FileHelper.uploadDinnerTable(image));
				imageDto.setDinnerTableDto(
						iDinnerTableService.getDinnerTableById(Integer.valueOf(response.getMessage())));
				imageDto.setRestaurantDto(restaurantDto);
				imageService.createImage(imageDto);
			}
			response.setMessage("Process Successfully");
			return "redirect:/partner/dinnerTable/index";
		}
		if (response.getOption() == 2) {
			dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
			dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
			model.addAttribute("msg", response);
			response = new ResultResponse<>(false, 0, "");
		}
		return "partner/dinnerTable/create";
	}

	@GetMapping("edit/{id}")
	public String showEditDinnerTableForm(@PathVariable("id") Integer id, Model model) {
		DinnerTableDto dinnerTableDto = iDinnerTableService.getDinnerTableById(id);
		Set<ImageDto> getExistingImages = imageService.getImagesByDinnerTableId(id);
		List<ImageDto> imageDtos = new ArrayList<>(getExistingImages);
		dinnerTableDto.setImagesDto(imageDtos);
		dinnerTableDto.setId(id);
		dinnerTableDto.setDinnerTableTypeDtoId(dinnerTableDto.getDinnerTableTypeDto().getId());
		dinnerTableDto.setRestaurantDtoId(dinnerTableDto.getRestaurantDto().getId());
		model.addAttribute("imageDtos", imageDtos);

		model.addAttribute("dinnerTableDto", dinnerTableDto);

		List<DinnerTableTypeDto> dinnerTableTypes = idinnerTableTypeService.getAllDinnerTablesType();
		dinnerTableDto.setDinnerTableTypeList(dinnerTableTypes);

		List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
		dinnerTableDto.setRestaurantList(restaurants);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		return "partner/dinnerTable/edit";
	}

	@PostMapping("edit/save")
	public String editDinnerTable(@ModelAttribute("dinnerTableDto") DinnerTableDto dinnerTableDto,
			BindingResult bindingResult, @RequestParam("images") MultipartFile[] images, Model model

	) {
		if (bindingResult.hasErrors()) {
			dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
			dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
			response.setOption(2);
			response.setMessage("Form input invalid");
			return "partner/dinnerTable/edit";
		}
		List<ImageDto> existingImages = new ArrayList<>(imageService.getImagesByDinnerTableId(dinnerTableDto.getId()));
		dinnerTableDto.setImagesDto(existingImages);
//        for (var existingImage:existingImages) {
//            dinnerTableDto.setImageDto(existingImage);
//        }
		var dinnerTableTypeDto = idinnerTableTypeService
				.getDinnerTableTypeById(dinnerTableDto.getDinnerTableTypeDtoId());
		dinnerTableDto.setDinnerTableTypeDto(dinnerTableTypeDto);
		var restaurantDto = iRestaurantService.getRestaurantById(dinnerTableDto.getRestaurantDtoId());
		dinnerTableDto.setRestaurantDto(restaurantDto);
		response = iDinnerTableService.updateDinnerTable(dinnerTableDto.getId(), dinnerTableDto);
		Set<String> imagePaths = new HashSet<>(); // Store paths of new images

		// Loop through new images
		for (MultipartFile image : images) {
			ImageDto imageDto = new ImageDto();
			imageDto.setPath(FileHelper.uploadDinnerTable(image));
			imageDto.setDinnerTableDto(iDinnerTableService.getDinnerTableById(Integer.valueOf(response.getMessage())));
			imagePaths.add(imageDto.getPath()); // Add new image path to the set

			// Check if the new image already exists in the existing images
			boolean isExisting = false;
			for (ImageDto existingImage : existingImages) {
				if (existingImage.getPath().equals(imageDto.getPath())) {
					isExisting = true;
					break;
				}
			}

			// If the new image does not exist in the existing images, create it
			if (!isExisting) {
				imageService.createImage(imageDto);
			}
		}

		// Loop through existing images and check if any image needs to be deleted
		for (ImageDto existingImage : existingImages) {
			if (!imagePaths.contains(existingImage.getPath())) {
				// If the path of the existing image is not present in the set of new image
				// paths,
				// delete the existing image
				imageService.deleteImage(existingImage.getId());
				FileHelper.deleteDinnerTableImage(existingImage.getPath());
			}
		}

		if (response.getOption() == 1) {
			response.setMessage("Process Successfully");
			return "redirect:/partner/dinnerTable/index";
		}
		if (response.getOption() == 2) {
			response.setMessage("Process Failure");
			dinnerTableDto.setDinnerTableTypeList(idinnerTableTypeService.getAllDinnerTablesType());
			dinnerTableDto.setRestaurantList(iRestaurantService.getAllRestaurants());
		}
		return "partner/dinnerTable/edit";
	}

	@GetMapping("delete/{id}")
	public String deleteDinnerTable(@PathVariable Integer id) {
		var existImages = imageService.getImagesByDinnerTableId(id);
		for (var existImage : existImages) {
			FileHelper.deleteDinnerTableImage(existImage.getPath());

			imageService.deleteImage(existImage.getId());
		}
		response = iDinnerTableService.deleteDinnerTable(id);
		return "redirect:/partner/dinnerTable";
	}
}
