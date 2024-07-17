package com.vanlang.hobby_station.controller;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.CategoryService;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SiteController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

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
        model.addAttribute("categories", categoryService.getAllCategories());
        return "layout";
    }

    @GetMapping ("/products-detail/{id}")
    public String detail(@PathVariable Long id, Model model){
        System.out.println("------------------------------------");
        if (!productService.getProductByIdAndIsDeleted(id, false).isPresent())
        {
            return "redirect:/shop";
        }
        Product product =  productService.getProductByIdAndIsDeletedOrThrow(id, false);
        model.addAttribute("product", product);
        model.addAttribute("content", "/site/detail-product");
        return "layout";
    }
    
}
