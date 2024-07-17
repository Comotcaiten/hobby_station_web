package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Brand;
import com.vanlang.hobby_station.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/brands")
public class BrandApiController {
    @Autowired
    private BrandService brandService;


    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PostMapping
    public void createBrand(@RequestBody Brand brand) {
        brandService.addBrand(brand);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found on :: " + id));
        return ResponseEntity.ok().body(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brandDetail) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found on :: " + id));
        brand.setName(brandDetail.getName());
        final Brand updatedBrand = brandService.addBrand(brand);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found on :: "
                + id));
        try {
            brand.getId();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        brandService.deleteBrand(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/management")
    public String showBrandManagement(Model model) {
        return "/brand";
    }
}
