package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.dtos.ResultResponse;
import com.bookingtable.models.Rate;
import com.bookingtable.repositories.RateRepository;
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
    private IImageService imageService;
    public ResultResponse<String> validation = new ResultResponse<String>(false,0,"");
    
    @GetMapping("/customer/dinnerTable-details/{id}")
    public String showDinnerTableDetailsPage(@PathVariable("id") Integer id, Model model, Principal principal) {
        // Lấy thông tin chi tiết của bàn ăn từ service
        var dinnerTable = dinnerTableService.getDinnerTableById(id);
        // Đưa thông tin chi tiết của bàn ăn vào model để hiển thị trên trang HTML
        Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
        dinnerTable.setImagesDto(new ArrayList<>(images));
        model.addAttribute("dinnerTable", dinnerTable);
        var listRates = new ArrayList<Rate>();
        var rates = rateRepository.findAll();
        for(var i : rates) {
        	var rate = new Rate();
        	if(i.getReservation().getDinnerTable().getId() == dinnerTable.getId()) {
        		 listRates.add(rate);
        	}
        }
        model.addAttribute("ratesLenght", listRates.size());
        model.addAttribute("rates", listRates);
        if(principal!=null) {
        	List<Rate> list = new ArrayList<>();
        	var commentPersonal = rateRepository.findByCustomerEmail(principal.getName());
        	for(var i : commentPersonal) {
        		if(i.getReservation().getReservationStatus().getStatus().equalsIgnoreCase("finished")) {
        			list.add(i);
        		}
        	}
        	model.addAttribute("commentPersonal", list);
        }
        model.addAttribute("msg", validation);
		validation = new ResultResponse<>(false, 0, "");
        return "customer/dinnerTable-details/index";
    }
}