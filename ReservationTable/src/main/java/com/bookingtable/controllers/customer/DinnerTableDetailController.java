package com.bookingtable.controllers.customer;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;
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
    
    
    @GetMapping("/customer/dinnerTable-details/{id}")
    public String showDinnerTableDetailsPage(@PathVariable("id") Integer id, Model model, Principal principal) {
        // Lấy thông tin chi tiết của bàn ăn từ service
        var dinnerTable = dinnerTableService.getDinnerTableById(id);
        // Đưa thông tin chi tiết của bàn ăn vào model để hiển thị trên trang HTML
        Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
        dinnerTable.setImagesDto(new ArrayList<>(images));
        model.addAttribute("dinnerTable", dinnerTable);
  
        var comments = rateRepository.findByDinnerTable_id(id);
        model.addAttribute("comments", comments);
        if(principal!=null) {
        	List<Rate> list = new ArrayList<>();
        	var commentPersonal = rateRepository.findByCustomerEmail(principal.getName());
        	for(var i : commentPersonal) {
        		if(i.isStatus()) {
        			list.add(i);
        		}
        	}
        	model.addAttribute("commentPersonal", list);
        }
        
        // Trả về tên của template HTML để hiển thị thông tin chi tiết của bàn ăn
        return "customer/dinnerTable-details/index"; // Chỉ định đường dẫn đúng tới template HTML
    }
}