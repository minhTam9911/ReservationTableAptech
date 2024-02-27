package com.bookingtable.controllers.system;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.servicies.IDinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DinnerTableController {

    @Autowired
    private IDinnerTableService iDinnerTableService;

    @GetMapping("/dinnerTables")
    public String getAllCustomers(Model model) {
        List<DinnerTableDto> dinnerTables = iDinnerTableService.getAllDinnerTables();
        model.addAttribute("dinnerTables", dinnerTables);
        return "dinnerTables-list";
    }
    // thêm create và update và delete
}
