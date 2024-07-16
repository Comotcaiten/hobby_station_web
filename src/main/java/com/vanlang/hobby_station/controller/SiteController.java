package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class SiteController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String home(Model model){
        model.addAttribute("products", productService.getProductsByIsDeleted(false));
        model.addAttribute("content", "/site/index");
        return "layout";
    }

    @GetMapping ("/shop")
    public String shop(Model model){
        model.addAttribute("products", productService.getProductsByIsDeleted(false));
        model.addAttribute("content", "/site/shop");
        return "layout";
    }
}
