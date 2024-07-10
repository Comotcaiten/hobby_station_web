package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.service.CategoryService;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class SiteController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;// Đảm bảo bạn đã inject CategoryService

    // Display a list of all products
    @GetMapping
    public String home(Model model){
        model.addAttribute("products", productService.getProductsByIsDeleted(false));
        return "index";
    }
}
