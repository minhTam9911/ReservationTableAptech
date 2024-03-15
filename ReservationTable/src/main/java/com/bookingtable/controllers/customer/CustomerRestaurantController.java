package com.bookingtable.controllers.customer;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.bookingtable.dtos.*;
import com.bookingtable.models.Collection;
import com.bookingtable.models.Rate;
import com.bookingtable.repositories.CustomerRepository;
import com.bookingtable.repositories.RateRepository;
import com.bookingtable.repositories.RestaurantRepository;
import com.bookingtable.servicies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("customer/restaurant")
public class CustomerRestaurantController {

    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IDinnerTableService dinnerTableService;
    @Autowired
    private ICollectionService collectionService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RateRepository rateRepository;

    @GetMapping({"index", "/", ""})
    public String index(Model model, Principal principal) {
        String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        model.addAttribute("requestURI", requestURI);

        List<RestaurantDto> data = new ArrayList<>();
        var restaurants = restaurantService.getAllRestaurants();
        for (var i : restaurants) {
            var restaurant = new RestaurantDto();
            restaurant = i;
            if (principal != null) {
                var customer = iCustomerService.getCustomerByEmail(principal.getName());
                if (customer != null) {
                    var collection = collectionService.findByCustomerAndRestaurant(customer.getId(), i.getId());
                    if (collection != null && collection.isStatus()) {
                        i.setCollectionStatus(true); // Collection tồn tại và có trạng thái true
                    } else {
                        i.setCollectionStatus(false); // Collection không tồn tại hoặc có trạng thái false
                    }
                } else {
                    for (int j = 0; j <= 2; j++) {
                        ImageDto image = new ImageDto();
                        var images = imageService.getImagesByRestaurantId(restaurant.getId()).stream().collect(Collectors.toList());
                        for (var x : images) {
                            if (x.getDinnerTableDto() == null) {
                                image = x;
                                restaurant.setImageSrc(image.getPath());
                            }
                        }

                    }
                    data.add(restaurant);
                    model.addAttribute("data", data);
                    return "customer/restaurant/index";
                }
            }
            for (int j = 0; j <= 2; j++) {
                ImageDto image = new ImageDto();
                var images = imageService.getImagesByRestaurantId(restaurant.getId()).stream().collect(Collectors.toList());
                for (var x : images) {
                    if (x.getDinnerTableDto() == null) {
                        image = x;
                        restaurant.setImageSrc(image.getPath());
                    }
                }
            }
            data.add(restaurant);
        }


        model.addAttribute("data", data);
        return "customer/restaurant/index";
    }


    @GetMapping("category/{id}")
    public String category(Model model, @PathVariable("id") Integer id) {
        var restaurants = restaurantService.getAllRestaurantsWithCategory(id);
        List<RestaurantDto> data = new ArrayList<>();
        for (var i : restaurants) {
            var restaurant = new RestaurantDto();
            restaurant = i;
            for (int j = 0; j <= 2; j++) {
                ImageDto image = new ImageDto();
                var images = imageService.getImagesByRestaurantId(restaurant.getId()).stream().collect(Collectors.toList());
                image = images.get(j);
                restaurant.setImageSrc(image.getPath());
            }
            data.add(restaurant);
        }
        model.addAttribute("data", data);
        return "customer/restaurant/category";
    }

    @GetMapping("{id}/dinnertable")
    public String dinnerTable(Model model, @PathVariable("id") String id) {
        var dinnerTables = dinnerTableService.getAllDinnerTablesForRestaurant(id);
        var restaurant = restaurantService.getRestaurantById(id);
        var imagesGet = imageService.getImagesByRestaurantId(id).stream().collect(Collectors.toList());
        var imageRestaurant = new ArrayList<ImageDto>();
        for (int j = 0; j < 3; j++) {
            ImageDto image = new ImageDto();
            image = imagesGet.get(j);
            imageRestaurant.add(image);
        }
        restaurant.setImagesDto(imageRestaurant);
        model.addAttribute("restaurant", restaurant);
        List<Rate> listRates = new ArrayList<>();
        for (DinnerTableDto dinnerTable : dinnerTables) {
            dinnerTable.getId();
            var rates = rateRepository.findAll();
            for (var i : rates) {
                var rate = new Rate();
                if (i.getReservation().getDinnerTable().getId() == dinnerTable.getId()) {
                    listRates.add(rate);
                }
            }

            Set<ImageDto> images = imageService.getImagesByDinnerTableId(dinnerTable.getId());
            dinnerTable.setImagesDto(new ArrayList<>(images));
        }
        model.addAttribute("rates", listRates);

        model.addAttribute("dinnerTables", dinnerTables);
        return "customer/restaurant/dinnerTableRestaurant";
    }

    @PostMapping("create-collection/{id}")
    public String addToCollection(@PathVariable("id") String restaurantId, Principal principal) {
        var restaurant = restaurantService.getRestaurantById(restaurantId);
        var customer = iCustomerService.getCustomerByEmail(principal.getName());
        var collection = new CollectionDto();
        collection.setStatus(true);
        collection.setRestaurant(restaurant);
        collection.setCustomer(customer);
        collectionService.insert(collection);
        return "redirect:/customer/restaurant/index";
    }

    @GetMapping("remove-collection/{id}")
    public String deleteCollection(@PathVariable("id") String restaurantId, Principal principal) {
        var customer = iCustomerService.getCustomerByEmail(principal.getName());
        var collectionIdToRemove = collectionService.findByCustomerAndRestaurant(customer.getId(), restaurantId);
        collectionService.delete(collectionIdToRemove.getId());
        return "redirect:/customer/restaurant";
    }

}
