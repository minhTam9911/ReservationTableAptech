package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.*;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.DinnerTableType;
import com.bookingtable.models.Reservation;
import com.bookingtable.models.Restaurant;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.*;
import com.bookingtable.servicies.implement.ImageService;
import com.bookingtable.servicies.implement.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
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

	@Autowired
	private IReservationService reservationService;
	@Autowired
	private  IRateService iRateService;
	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index(Model model, @Param("keyword") String keyword) {
		String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
		model.addAttribute("requestURI", requestURI);

		// Lấy danh sách tất cả các bàn ăn
		List<DinnerTableDto> dinnerTables = iDinnerTableService.getAllDinnerTables();

		// Tạo một Map để lưu trữ số lượng comment và điểm của mỗi bàn
		Map<DinnerTableDto, Integer> commentCounts = new HashMap<>();
		Map<DinnerTableDto, Integer> totalPoints = new HashMap<>();

		// Lặp qua từng bàn
		for (var dinnerTable : dinnerTables) {
			// Lấy danh sách các đặt bàn cho bàn hiện tại
			List<ReservationDto> reservations = reservationService.findByDinnerTableId(dinnerTable.getId());
			int totalPoint = 0;
			for (ReservationDto reservation : reservations) {
				var rates = iRateService.getRateByReservationId(reservation.getId());
				int commentCount = rates.size();
				for (var comment : rates){
					totalPoint += comment.getId();
				}
				commentCounts.put(dinnerTable, commentCount);

			}
			// Lưu số lượng comment và tổng điểm vào Map
			totalPoints.put(dinnerTable, totalPoint);
		}
		// Sắp xếp danh sách các bàn theo số lượng comment và điểm từ cao đến thấp
		List<DinnerTableDto> topThreeTables = dinnerTables.stream()
				.sorted(Comparator.comparingInt(table -> {
					int commentCount = commentCounts.getOrDefault(table, 0);
					int totalPoint = totalPoints.getOrDefault(table, 0);
					return commentCount + totalPoint;
				}).reversed())
				.limit(3)
				.collect(Collectors.toList());
		for (DinnerTableDto dinnerTable : topThreeTables) {
			Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
			dinnerTable.setImagesDto(new ArrayList<>(images));
		}
		// Lấy danh sách đánh giá của top 3 bàn
		List<List<RateDto>> topThreeTablesRates = new ArrayList<>();
		for (DinnerTableDto dinnerTable : topThreeTables) {
			List<ReservationDto> reservations = reservationService.findByDinnerTableId(dinnerTable.getId());
			List<RateDto> rates = new ArrayList<>();
			for (ReservationDto reservation : reservations) {
				rates.addAll(iRateService.getRateByReservationId(reservation.getId()));
			}
		}
		// Tính điểm trung bình của mỗi đánh giá
		List<Double> averageRates = new ArrayList<>();
		for (var rates : topThreeTablesRates) {
			double totalPointByRates = 0;
			for (RateDto rate : rates) {
				totalPointByRates += rate.getPoint();
			}
			double average = totalPointByRates / rates.size();
			long roundedAverage = Math.round(average);
			model.addAttribute("roundedAverage",roundedAverage);
			averageRates.add(average);
		}
		// Gửi danh sách các bàn có số lượng comment và điểm cao nhất đến view
		model.addAttribute("topThreeTables", topThreeTables);
		model.addAttribute("topThreeTablesRates", topThreeTablesRates);
		var restaurants = restaurantRepository.findAll();
		model.addAttribute("restaurants", restaurants);

		return "customer/home/index";
	}

}
