package com.vanlang.hobby_station.controller.api;


import com.vanlang.hobby_station.model.Category;
import com.vanlang.hobby_station.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getProductById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new RuntimeException("Category not found on :: " + id));
        return ResponseEntity.ok().body(category);
    }
}
