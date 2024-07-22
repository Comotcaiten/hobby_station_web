package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    
    @Autowired
    private ProductService productService;

    // Cho mọi người dùng
    @GetMapping
    public List<Product> getAllProducts() {
        // Lấy tất cả sản phẩm
        return productService.getProductsByIsDeleted(false);
    }

    // Cho mọi người dùng
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        return ResponseEntity.ok().body(product);
    }

    // --- Name - Searching  ---

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.trim().isEmpty()) {
            return productService.getAllProducts();
        } else {
            return productService.searchProductsByName(name);
        }
    }

    // --- Brand ---

    @GetMapping("/brands/{id}")
    public List<Product> getProductsByBrandAndIsDeletedTrue(@PathVariable Long id) {
        return productService.getProductsByBrandAndIsDeleted(id, true);
    }
    
    // --- Category ---

    @GetMapping("/categories/{id}")
    public List<Product> getAllProductsByCategory(@PathVariable Long id) {
        return productService.getProductsByCategoryIdAndIsDeleted(id, false);
    }

    // --- News ---
    @GetMapping("/newest")
    public List<Product> getNewestProducts() {
        // Lấy danh sách các sản phẩm mới nhất với isDelete = false và tối đa là 4 sản phẩm
        return productService.getNewestProducts(4);
    }

    // --- Selling ---
    // lấy tất cả với điều kiện là status của order = Deliveried và isDelete của products = false
    // Trả về List Product
    @GetMapping("/top-selling")
    public ResponseEntity<List<Product>> getTopSellingProductsObj() {
        // Lấy giới hạn 4 sản phẩm
        List<Product> topSellingProducts = productService.getTopSellingProducts(4);
        return new ResponseEntity<>(topSellingProducts, HttpStatus.OK);
    }
    
}
