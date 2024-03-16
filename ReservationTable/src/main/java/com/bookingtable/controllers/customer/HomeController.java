package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.DinnerTableTypeDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.models.DinnerTableType;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IDinnerTableTypeService;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.implement.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping({ "", "customer/home", "/","home" })
public class HomeController {
	@Autowired
	private IDinnerTableTypeService iDinnerTableTypeService;

	@Autowired
	private IDinnerTableService iDinnerTableService;
	@Autowired
	private IImageService imageService;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index(Model model) {
		String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
		model.addAttribute("requestURI", requestURI);
		// Lấy danh sách tất cả các loại bàn ăn
		List<DinnerTableTypeDto> dinnerTableTypes = iDinnerTableTypeService.getAllDinnerTablesType();
		// Tạo một map lưu trữ hình ảnh đầu tiên của mỗi loại bàn ăn
		Map<Integer, String> typeImagesMap = new HashMap<>();
		for (DinnerTableTypeDto type : dinnerTableTypes) {
			// Lấy danh sách các bàn ăn thuộc loại này
			List<DinnerTableDto> dinnerTables = iDinnerTableService.getDinnerTablesByType(type);
			for (DinnerTableDto dinnerTableDto : dinnerTables) {
				// Lấy danh sách hình ảnh của bàn ăn này
				Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTableDto.getId());
				if (!images.isEmpty()) {
					// Lấy đường dẫn hình ảnh đầu tiên và thêm vào map
					String imagePath = images.iterator().next().getPath();
					typeImagesMap.put(type.getId(), imagePath);
					// Sau khi tìm thấy ảnh của bàn ăn này, thoát khỏi vòng lặp để không lặp lại
					break;
				}
			}
		}

		model.addAttribute("dinnerTableTypes", dinnerTableTypes);
		model.addAttribute("typeImagesMap", typeImagesMap);
		return "customer/home/index";
	}
}
