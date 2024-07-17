package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductApiController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        System.out.println("Post " + product.getId());
        return productService.addProduct(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        System.out.println("Get " + id);
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        System.out.println("PUT");
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        final Product updatedProduct = productService.addProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: "
                + id));
        try {
            product.getId();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    // Brand

    @GetMapping("/brands/{id}")
    public List<Product> getAllProductsByBrand(@PathVariable Long id) {
        return productService.getProductsByBrand(id);
    }

    @GetMapping("/brands-false/{id}")
    public List<Product> getProductsByBrandAndIsDeletedFalse(@PathVariable Long id) {
        return productService.getProductsByBrandAndIsDeleted(id, false);
    }

    @GetMapping("/brands-true/{id}")
    public List<Product> getProductsByBrandAndIsDeletedTrue(@PathVariable Long id) {
        return productService.getProductsByBrandAndIsDeleted(id, true);
    }
    
    // Category

    @GetMapping("/categories/{id}")
    public List<Product> getAllProductsByCategory(@PathVariable Long id) {
        return productService.getProductsByCategory(id);
    }

    // Price

    @GetMapping("/less/{id}")
    public List<Product> getAllProductsLess(@PathVariable Double id) {
        return productService.getProductsByPriceLessThan(id);
    }

    @GetMapping("/greater/{id}")
    public List<Product> getAllProductsGreater(@PathVariable Double id) {
        return productService.getProductsByPriceGreaterThan(id);
    }

    @GetMapping("/equal/{id}")
    public List<Product> getAllProductsEqual(@PathVariable Double id) {
        return productService.getProductsByPriceEquals(id);
    }
}
