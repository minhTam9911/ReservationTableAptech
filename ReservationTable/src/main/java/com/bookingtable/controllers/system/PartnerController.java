package com.bookingtable.controllers.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookingtable.dtos.RestaurantStatisticalDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.mappers.RestaurantStatisticalMapper;
import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.Invoice;
import com.bookingtable.models.RestaurantStatistical;
import com.bookingtable.repositories.DinnerTableRepository;
import com.bookingtable.repositories.InvoiceRepository;
import com.bookingtable.repositories.ReceptionistRepository;
import com.bookingtable.repositories.ReservationAgentRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.IReservationService;
import com.bookingtable.servicies.IRestaurantService;
import com.bookingtable.servicies.IRestaurantStatisticalService;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.text.DateFormatter;

@Controller
@RequestMapping("partner")
public class PartnerController {

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private IRestaurantStatisticalService restaurantStatisticalService;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private DinnerTableRepository dinnerTableRepository;
	@Autowired
	private IReservationService iReservationService;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired 
	private ReceptionistRepository receptionistRepository;
	@Autowired
	private ReservationAgentRepository agentRepository;

	private ResultResponse<String> response = new ResultResponse<>(false, 0, "");

	@RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
	public String index(Model model, Authentication authentication) {
		model.addAttribute("username", authentication.getName());
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		var restaurants = restaurantRepository.findByReservationAgentEmail(authentication.getName());
		
		List<Object> chartData = new ArrayList<>();
		var totalRestaurant = restaurants.size();
		var totalDinnerTable = 0;
		var totalReservation = 0;
		var totalFinished = 0;
		if(restaurants.size()>0) {
			chartData.addAll(restaurantStatisticalService.findAll(authentication.getName(), restaurants.get(0).getId()).stream()
					.map(k -> getChartData(k)).collect(Collectors.toList()));
			for (var i : restaurants) {
				
				totalDinnerTable += dinnerTableRepository.findByRestaurant_Id(i.getId()).size();
				var reservations = reservationRepository.findByRestaurantId(i.getId());
				totalReservation += reservations.size();
				for (var j : reservations) {
					if (j.getReservationStatus().getId() == 3) {
						totalFinished++;
					}
				}
			}

		}
		
		
		model.addAttribute("totalRestaurant", totalRestaurant);
		model.addAttribute("totalDinnerTable", totalDinnerTable);
		model.addAttribute("totalReservation", totalReservation);
		model.addAttribute("totalFinished", totalFinished);
		model.addAttribute("chartData", chartData);
		System.out.println(chartData);
		return "partner/index";
	}

	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	private Object getChartData(RestaurantStatistical dto) {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/yyyy");
		return List.of(dto.getDate().format(formatters), dto.getTotalBookinged(), dto.getTotalFinished(), dto.getTotalCanceled(),
				dto.getTotalDinnerTable(), dto.getTotalCustomer());
	}
	
	
	@GetMapping("dinner-table")
	public String dinnerTable(Model model, Principal principal) {
		var restaurants = restaurantRepository.findByReservationAgentEmail(principal.getName());
		List<DinnerTable> data = new ArrayList<>();
		for(var i : restaurants) {
			data.addAll(dinnerTableRepository.findByRestaurant_Id(i.getId()));
		}
		model.addAttribute("data",data);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false,0,"");
		return "partner/invoice/dinnerTable";
	}
	
	@GetMapping("dinner-table/invoice/{id}")
	public String invoiceDinnerTable(Model model, Principal principal, @PathVariable("id") Integer id) {
		var invoices = invoiceRepository.findAll();
		List<Invoice> data = new ArrayList<>();
		for(var i : invoices) {
			if(i.getReservation().getDinnerTable().getId()== id) {
				data.add(i);
			}
		}
		model.addAttribute("data",data);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false,0,"");
		return "partner/invoice/invoiceDinnerTable";
	}
	
	@GetMapping("dinner-table/invoice/detail/{id}")
	public String detalInvoiceDinnerTable(Model model, Principal principal, @PathVariable("id") String id) {
		var receptionist = receptionistRepository.findByEmail(principal.getName());
		var invoices = invoiceRepository.findAll();
		Invoice data = new Invoice();
		for(var i : invoices) {
			if(i.getId().equals(id)) {
				data = i;
			}
		}
		model.addAttribute("data",data);
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false,0,"");
		return "partner/invoice/detailCustomerDinnerTable";
	}
	

	@GetMapping("invoice")
	public String invoice(Model model, Principal principal) {
		var res = restaurantRepository.findByReservationAgentEmail(principal.getName());
		var invoices = invoiceRepository.findAll();
		List<Invoice> data = new ArrayList<Invoice>();
		for(var x : res) {
			for(var i : invoices) {
				if(i.getReservation().getRestaurant().getId().equals(x.getId())) {
					data.add(i);
				}
			}
		}
		
		model.addAttribute("msg", response);
		model.addAttribute("data",data);
		response = new ResultResponse<>(false,0,"");
		return "partner/invoice/invoiceRestaurant";
	}
	
	@GetMapping("restaurant/chart/{id}")
	public String chart(Model model, Principal principal, @PathVariable("id")String id) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("msg", response);
		response = new ResultResponse<>(false, 0, "");
		var restaurants = restaurantRepository.findByReservationAgentEmail(principal.getName());
		List<Object> chartData = new ArrayList<>();
		var totalRestaurant = restaurants.size();
		var totalDinnerTable = 0;
		var totalReservation = 0;
		var totalFinished = 0;
		chartData.addAll(restaurantStatisticalService.findAll(principal.getName(), id).stream()
				.map(k -> getChartData(k)).collect(Collectors.toList()));
		if(restaurants.size()>0) {
			for (var i : restaurants) {
				
				totalDinnerTable += dinnerTableRepository.findByRestaurant_Id(i.getId()).size();
				var reservations = reservationRepository.findByRestaurantId(i.getId());
				totalReservation += reservations.size();
				for (var j : reservations) {
					if (j.getReservationStatus().getId() == 3) {
						totalFinished++;
					}
				}
			}
		}
		

		model.addAttribute("totalRestaurant", totalRestaurant);
		model.addAttribute("totalDinnerTable", totalDinnerTable);
		model.addAttribute("totalReservation", totalReservation);
		model.addAttribute("totalFinished", totalFinished);
		model.addAttribute("chartData", chartData);
		System.out.println(chartData);
		return "partner/index";
	}
	
	
	
	
}
