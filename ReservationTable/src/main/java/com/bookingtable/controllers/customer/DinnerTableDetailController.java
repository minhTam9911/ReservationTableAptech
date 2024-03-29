package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.models.Rate;
import com.bookingtable.models.Reservation;
import com.bookingtable.repositories.RateRepository;
import com.bookingtable.repositories.ReservationRepository;
import com.bookingtable.servicies.IDinnerTableService;
import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class DinnerTableDetailController {

    @Autowired
    private IDinnerTableService dinnerTableService;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private IImageService imageService;
    public static ResultResponse<String> validation = new ResultResponse<String>(false,0,"");
    
    @GetMapping("/customer/dinnerTable-details/{id}")
    public String showDinnerTableDetailsPage(@PathVariable("id") Integer id, Model model, Principal principal) {
        var dinnerTable = dinnerTableService.getDinnerTableById(id);
        Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
        dinnerTable.setImagesDto(new ArrayList<>(images));
        model.addAttribute("dinnerTable", dinnerTable);
        model.addAttribute("dinnerTableId", dinnerTable.getId());
        var listRates = new ArrayList<Rate>();
        var rates = rateRepository.findAll();
        for(var i : rates) {
   
        	if((i.getReservation().getDinnerTable().getId() == id) && !i.isStatus()) {
        		 listRates.add(i);
        	}
        }

        model.addAttribute("rates", listRates);
        if(principal!=null) {
        	Rate list = new Rate();
        	var commentPersonal = rateRepository.findByCustomerEmail(principal.getName());
            if(commentPersonal!=null) {
                for (var i : commentPersonal) {
                    if (i.getReservation().getReservationStatus().getId()==3 && i.isStatus()) {
                    	if(i.getReservation().getDinnerTable().getId()==id) {
                    		list = i;
                    	}
                        
                    }
                }
                var count = 0;
                if(list.getId() == null) {
                	count = 1;
                }else {
                	count = 2;
                }
                model.addAttribute("commentPersonalLenght", count);
                model.addAttribute("commentPersonal", list);
                
            }
        }
        var reservation = reservationRepository.findByDinnerTableId(id);
        List<Reservation> totalReservation = new ArrayList<>();
        for(var i : reservation) {
        	if(!(i.getReservationStatus().getId()==4)) {
        		totalReservation.add(i);
        	}
        }
        
        System.out.println(listRates.size());
        model.addAttribute("totalReservation", totalReservation.size());
	        model.addAttribute("totalRate", listRates.size());
	        model.addAttribute("msg", validation);
        return "customer/dinnerTable-details/index";
    }
}