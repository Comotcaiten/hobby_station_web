package com.vanlang.demo.controller;

import com.vanlang.demo.model.Brand;
import com.vanlang.demo.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BrandController {
    @Autowired
    private final BrandService brandService;

    @GetMapping("/brands/add")
    public String showAddForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "/brands/add-brand";
    }

    @PostMapping("/brands/add")
    public String addBrand(@Valid Brand brand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/brands/add-brand";
        }
        brandService.addBrand(brand);
        return "redirect:/brands";
    }

    //Hiển thị các danh mục
    @GetMapping("/brands")
    public String listBrands(Model model){
        List<Brand> brands = brandService.getAllBrand();
        model.addAttribute("brand",brands);
        return "/brands/brands-list";
    }

    // GET request to show brands edit form
    @GetMapping("/brands/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new IllegalArgumentException("Invalid brand Id:" + id));
        model.addAttribute("brand", brand);
        return "/brands/update-brand";
    }

    // POST request to update brands
    @PostMapping("/brands/update/{id}")
    public String updateBrands(@PathVariable("id") Long id, @Valid Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            brand.setId(id);
            return "/brands/update-brand";
        }
        brandService.updateBrand(brand);
        model.addAttribute("brand", brandService.getAllBrand());
        return "redirect:/brands";
    }

    // GET request for deleting brands
    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new IllegalArgumentException("Invalid brand Id:" + id));
        brandService.deleteBrand(id);
        model.addAttribute("brand", brandService.getAllBrand());
        return "redirect:/brands";
    }
}
