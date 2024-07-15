package com.vanlang.hobby_station.controller.api;


import com.vanlang.hobby_station.model.Category;
import com.vanlang.hobby_station.model.Product;
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

    @PostMapping
    public void createCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getProductById(@PathVariable Long id) {
        System.out.println("Get " + id);
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new RuntimeException("Category not found on :: " + id));
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateProduct(@PathVariable Long id, @RequestBody Category categoryDetails) {
        System.out.println("PUT");
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new RuntimeException("Category not found on :: " + id));
        category.setName(categoryDetails.getName());
        final Category updatedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new RuntimeException("Category not found on :: "
//                + id));
//        categoryService.destroyCategory(id);
//        return ResponseEntity.ok().build();
//    }
}
