package com.vanlang.hobby_station.controller.admin;

import com.vanlang.hobby_station.model.Brand;
import com.vanlang.hobby_station.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/brands")
public class BrandCrudController {
    @Autowired
    private final BrandService brandService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "/brands/add-brand";
    }

    @PostMapping("/add")
    public String addBrand(@Valid Brand brand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/brands/add-brand";
        }
        brandService.addBrand(brand);
        return "redirect:/admin/brands";
    }

    @GetMapping()
    public String listBrands(Model model){
        model.addAttribute("brands",brandService.getAllBrands());
        return "/brands/brands-list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new IllegalArgumentException("Invalid brand Id:" + id));
        model.addAttribute("brand", brand);
        return "/brands/update-brand";
    }

    @PostMapping("/update/{id}")
    public String updateBrands(@PathVariable("id") Long id, @Valid Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            brand.setId(id);
            return "/brands/update-brand";
        }
        brandService.updateBrand(brand);
        return "redirect:/admin/brands";
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, Model model) {
        brandService.deleteBrand(id);
        return "redirect:/admin/brands";
    }
}
