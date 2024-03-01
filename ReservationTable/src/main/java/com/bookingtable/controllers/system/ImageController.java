package com.bookingtable.controllers.system;

import com.bookingtable.servicies.IImageService;
import com.bookingtable.servicies.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("system/image" )
public class ImageController {
    @Autowired
    private IImageService imageService;
    @RequestMapping(value = { "index", "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("data", imageService.getAllImages());
        return "system/image/index";
    }
}
