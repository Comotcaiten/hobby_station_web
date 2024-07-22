package com.vanlang.hobby_station.controller.api;

import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.model.Order.OrderStatus;
import com.vanlang.hobby_station.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin
@RestController
@RequestMapping("/api/admin/products")
public class ProductApiAdminController {

    
    @Autowired
    private ProductService productService;

    // Cho mọi người dùng
    @GetMapping
    public List<Product> getAllProducts() {
        // Lấy tất cả sản phẩm
        return productService.getProductsByIsDeleted(false);
    }

    // Lấy tất cả sản phẩm đã xóa
    @GetMapping({"/trashed", "/trash-can"})
    public List<Product> getAllProductsIsDeleted() {
        // Lấy tất cả sản phẩm
        return productService.getProductsByIsDeleted(true);
    }

    // Post - Yêu cầu quyền admin
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // Post restore - Yêu cầu quyền admin
    // Khôi phục lại sản phẩm đã xóa
    @PostMapping("/restore/{id}")
    public ResponseEntity<Void> restoreProduct(@PathVariable Long id) {
        productService.restoreProductById(id);
        return ResponseEntity.ok().build();
    }

    // Cho mọi người dùng
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        return ResponseEntity.ok().body(product);
    }

    // Delete - Yêu cầu quyền admin
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

    // @GetMapping("/brands/{id}")
    // public List<Product> getAllProductsByBrand(@PathVariable Long id) {
    //     return productService.getProductsByBrand(id);
    // }

    @GetMapping("/brands/{id}")
    public List<Product> getProductsByBrandAndIsDeletedTrue(@PathVariable Long id) {
        return productService.getProductsByBrandAndIsDeleted(id, true);
    }
    
    // --- Category ---

    @GetMapping("/categories/{id}")
    public List<Product> getAllProductsByCategoryAndIsDeleted(@PathVariable Long id) {
        return productService.getProductsByCategoryIdAndIsDeleted(id, false);
    }

    // --- News ---
    @GetMapping("/newest")
    public List<Product> getNewestProducts() {
        // Lấy danh sách các sản phẩm mới nhất với isDelete = false và tối đa là 4 sản phẩm
        return productService.getNewestProducts(4);
    }

    // --- Selling ---
    // Lưu ý là Object = [id của sản phẩm, số lượng sản phẩm bán ra]
    // Trả về các sản phẩm top selling dạng Object [id-product, số lượng bán ra]
    // lấy tất cả và bỏ qua các điều kiện
    // Trả về List Object
    @GetMapping("/top-selling/all")
    public ResponseEntity<List<Object[]>> getTopSellingProducts() {
        List<Object[]> results = productService.getSelling();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    // lấy tất cả với điều kiện là status của order = Deliveried và isDelete của products = false
    // Trả về List Product
    @GetMapping("/top-selling")
    public ResponseEntity<List<Product>> getTopSellingProductsObj() {
        // Lấy giới hạn 4 sản phẩm
        List<Product> topSellingProducts = productService.getTopSellingProducts(4);
        return new ResponseEntity<>(topSellingProducts, HttpStatus.OK);
    }

    // lấy tất cả với điều kiện là status của order = Deliveried
    // Trả về List Object
    @GetMapping("/top-selling/delivered")
    public ResponseEntity<List<Object[]>> getTopSellingProductsDilivery() {
        List<Object[]> results = productService.getTopSellingProductsByStatus(OrderStatus.DELIVERED);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
}
