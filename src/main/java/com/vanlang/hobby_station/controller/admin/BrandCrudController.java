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

        model.addAttribute("content", "/brands/add-brand");
        return "dashboard-layout";
    }

    @PostMapping("/add")
    public String addBrand(@Valid Brand brand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            
            model.addAttribute("content", "/brands/add-brand");
            return "dashboard-layout";
        }
        brandService.addBrand(brand);
        return "redirect:/admin/brands";
    }

    //Hiển thị các danh mục
    @GetMapping()
    public String listBrands(Model model){
        model.addAttribute("brands",brandService.getAllBrands());

        model.addAttribute("content", "/brands/brands-list");
        return "dashboard-layout";
    }

    // GET request to show brands edit form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new IllegalArgumentException("Invalid brand Id:" + id));
        model.addAttribute("brands", brand);

        model.addAttribute("content", "/brands/update-brand");
        return "dashboard-layout";
    }

    // POST request to update brands
    @PostMapping("/update/{id}")
    public String updateBrands(@PathVariable("id") Long id, @Valid Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            brand.setId(id);

            model.addAttribute("content", "/brands/update-brand");
            return "dashboard-layout";
        }
        brandService.updateBrand(brand);
        model.addAttribute("brands", brandService.getAllBrands());
        return "redirect:/admin/brands";
    }

    // GET request for deleting brands
    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, Model model) {
        // Brand brand = brandService.getBrandById(id).orElseThrow(() -> new IllegalArgumentException("Invalid brand Id:" + id));
        // try {
        //     brand.getId();
        // } catch (Exception e) {
        //     // TODO: handle exception
        //     System.out.println(e);
        // }
        // brandService.deleteBrand(id);
        // model.addAttribute("brands", brandService.getAllBrand());
        return "redirect:/admin/brands";
    }
}
