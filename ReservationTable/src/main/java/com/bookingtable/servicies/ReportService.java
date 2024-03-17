package com.bookingtable.servicies;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bookingtable.models.RestaurantStatistical;
import com.bookingtable.models.RevenueStatistics;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.InvoiceRepository;
import com.bookingtable.repositories.RateRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.repositories.RestaurantRepository;

@Component
public class ReportService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ReservationAgentRepository reservationAgentRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private IRestaurantStatisticalService restaurantStatisticalService;
	@Autowired
	private IRevenueStatisticsService revenueStatisticsService;

@Scheduled(cron = "0 0 0 1 * ?")
	//@Scheduled(fixedRate = 20000)
	public void generateMonthlyReport() {
		System.out.println("---------------------------Test---------------------");
		var currentMonth = LocalDate.now();
		var lastMonth = LocalDate.now().minusMonths(1);
		///// -----Statiscal Restaurant------/////

		var restaurants = restaurantRepository.findAll();

		for (var i : restaurants) {
			var restaurantStatistical = new RestaurantStatistical();
			Integer totalBookinged = 0;
			Integer totalFinished = 0;
			Integer totalCanceled = 0;
			Integer totalCustomer = 0;
			String restaurant = i.getId();
			Integer totalDinnerTable = 0;
			var reservations = reservationRepository.findByRestaurantId(i.getId());
			for (var j : reservations) {
				if (j.getBookingDate().isBefore(currentMonth) && j.getBookingDate().isAfter(lastMonth)) {
					totalBookinged++;
					totalCustomer++;
					if (j.getReservationStatus().getId() == 3) {
						totalFinished++;
					}
					if (j.getReservationStatus().getId() == 4) {
						totalCanceled++;
					}
				}

			}
			var dinnerTables = dinnerTableRepository.findByRestaurant_Id(restaurant);
			totalDinnerTable = dinnerTables.size();
			restaurantStatistical.setDate(currentMonth);
			restaurantStatistical.setTotalBookinged(totalBookinged);
			restaurantStatistical.setTotalFinished(totalFinished);
			restaurantStatistical.setTotalCanceled(totalCanceled);
			restaurantStatistical.setTotalCustomer(totalCustomer);
			restaurantStatistical.setRestaurant(restaurant);
			restaurantStatistical.setTotalDinnerTable(totalDinnerTable);
			restaurantStatisticalService.create(restaurantStatistical);
		}

		///// -----------//////

		///// -----Revenue Statictical------//////

		var reservations = reservationRepository.findAll();
		var invoices = invoiceRepository.findAll();
		var revenueStatistics = new RevenueStatistics();
		Double totalAmount = 0.0;
		Integer totalBookinged = 0;
		Integer totalInvoice = 0;
		Integer totalFinished = 0;
		Integer totalCanceled = 0;
		Integer totalCustomer = 0;
		Integer totalAgent = 0;
		Integer totalRestaurant = 0;
		Integer totalDinnerTable = 0;
		Integer totalComment = 0;
		Integer level1 = 0;
		Integer level2 = 0;
		Integer level3 = 0;
		Integer level4 = 0;
		Integer level5 = 0;

		for (var i : reservations) {
			if (i.getCreated().toLocalDate().isBefore(currentMonth)
					&& i.getCreated().toLocalDate().isAfter(lastMonth)) {
				totalBookinged++;
				totalCustomer++;
				if (i.getReservationStatus().getId() == 3) {
					totalFinished++;
				}
				if (i.getReservationStatus().getId() == 4) {
					totalCanceled++;
				}
			}

		}

		for (var i : invoices) {
			if (i.getReservation().getCreated().toLocalDate().isBefore(currentMonth)
					&& i.getReservation().getCreated().toLocalDate().isAfter(lastMonth)) {
				totalInvoice++;
				totalAmount += i.getReservation().getDinnerTable().getDinnerTableType().getPrice();
			}

		}
		totalAgent = reservationAgentRepository.findAll().size();
		totalRestaurant = restaurantRepository.findAll().size();
		totalDinnerTable = dinnerTableRepository.findAll().size();
		var comments = rateRepository.findAll();

		for (var i : comments) {
			if (i.getCreated().isBefore(currentMonth) && i.getCreated().isAfter(lastMonth)) {
				if (!i.isStatus()) {
					totalComment++;
					if (i.getPoint() == 1) {
						level1++;
					}
					if (i.getPoint() == 2) {
						level2++;
					}
					if (i.getPoint() == 3) {
						level3++;
					}
					if (i.getPoint() == 4) {
						level4++;
					}
					if (i.getPoint() == 5) {
						level5++;
					}
				}

			}
		}

		revenueStatistics.setDate(currentMonth);
		revenueStatistics.setLevel1(level1);
		revenueStatistics.setLevel2(level2);
		revenueStatistics.setLevel3(level3);
		revenueStatistics.setLevel4(level4);
		revenueStatistics.setLevel5(level5);
		revenueStatistics.setTotalAmount(totalAmount);
		revenueStatistics.setTotalBookinged(totalBookinged);
		revenueStatistics.setTotalInvoice(totalInvoice);
		revenueStatistics.setTotalFinished(totalFinished);
		revenueStatistics.setTotalCanceled(totalCanceled);
		revenueStatistics.setTotalCustomer(totalCustomer);
		revenueStatistics.setTotalAgent(totalAgent);
		revenueStatistics.setTotalRestaurant(totalRestaurant);
		revenueStatistics.setTotalDinnerTable(totalDinnerTable);
		revenueStatistics.setTotalComment(totalComment);
		revenueStatisticsService.create(revenueStatistics);
		///// -----------//////
	}

}
