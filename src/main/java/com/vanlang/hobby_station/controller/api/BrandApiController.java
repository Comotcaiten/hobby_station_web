package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Brand;
import com.vanlang.hobby_station.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found on :: " + id));
        return ResponseEntity.ok().body(brand);
    }
}
