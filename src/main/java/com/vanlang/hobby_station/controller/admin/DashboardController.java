package com.vanlang.hobby_station.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanlang.hobby_station.service.CategoryService;
import com.vanlang.hobby_station.service.ProductService;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    // @Autowired
    // private ProductService productService;
    // @Autowired
    // private CategoryService categoryService;

    @GetMapping
    public String home() {
        return "/dashboard/index";
    }

    @GetMapping("/dashboard")
    public String reHome() {
        return "redirect:/admin";
    }
}
