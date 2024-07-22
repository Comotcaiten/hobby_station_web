package com.vanlang.hobby_station.service;

import com.vanlang.hobby_station.model.Order.OrderStatus;
import com.vanlang.hobby_station.model.Product;
import com.vanlang.hobby_station.repository.OrderDetailRepository;
import com.vanlang.hobby_station.repository.ProductRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private final ProductRepository productRepository;
    
    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve products by isDeleted status
    public List<Product> getProductsByIsDeleted(boolean isDeleted) {
        return productRepository.findByIsDeleted(isDeleted);
    }

    // By Name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingAndIsDeleted(name, false);
    }
    
    // Retrieve products by Brand status
    public List<Product> getProductsByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    // Retrieve products by isDeleted status and Brand
    public List<Product> getProductsByBrandAndIsDeleted(Long brandId, Boolean isDeleted) {
        return productRepository.findByBrandIdAndIsDeleted(brandId, isDeleted);
    }

    // Retrieve products by Category status
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByCategoryIdAndIsDeleted(Long categoryId, boolean isDeleted) {
        return productRepository.findByCategoryIdAndIsDeleted(categoryId, isDeleted);
    }

    // Retrieve products by Price
    // Product.price > Price
    public List<Product> getProductsByPriceGreaterThan(Double price) {
        return productRepository.findByPriceGreaterThan(price);
    }

    // Product.price < Price
    public List<Product> getProductsByPriceLessThan(Double price) {
        return productRepository.findByPriceLessThan(price);
    }

    // Product.price = Price
    public List<Product> getProductsByPriceEquals(Double price) {
        return productRepository.findByPriceEquals(price);
    }

    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByIdAndIsDeleted(Long id, boolean isDeleted) {
        return productRepository.findByIdAndIsDeleted(id, isDeleted);
    }

    public Product getProductByIdAndIsDeletedOrThrow(Long id, boolean isDeleted) {
        return productRepository.findByIdAndIsDeleted(id, isDeleted)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + id + " and isDeleted " + isDeleted + " does not exist."));
    }
    

    public Product getProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + id + " does not exist."));
    }

    // Add a new product to the database
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(@NonNull Product product) {
        Product existingProduct = productRepository.findById(product.getId()) .orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setImg(product.getImg());
        return productRepository.save(existingProduct);
    }
    // Delete a product by its id
    public void destroyProductById(Long id) {
        if(!productRepository.existsById(id)){
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    // Soft delete a product by its id
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + id + " does not exist."));
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    // Restore a soft deleted product by its id
    public void restoreProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + id + " does not exist."));
        product.setIsDeleted(false);
        productRepository.save(product);
    }
    
    // News products
    // Lấy danh sách các sản phẩm mới nhất với isDelete = false
    public List<Product> getNewestProducts(int limit) {

        // Lấy danh sách sản phẩm đã sắp xếp theo thời gian tạo
        List<Product> allProducts = productRepository.findAllByIsDeletedOrderByCreatedAtDesc(false);

        // Đảm bảo n không vượt quá kích thước của danh sách
        limit = Math.min(limit, allProducts.size());

        // Lấy n sản phẩm đầu tiên từ danh sách
        return allProducts.subList(0, limit);
    }

    // Danh sách tổng sản lượng bán ra của từng sản phẩm
    // lấy tất cả bỏ qua điều kiện
    public List<Object[]> getSelling() {
        return orderDetailRepository.findTopSellingProducts();
    }
    
    // lấy tất cả với điều kiện là status của order
    public List<Object[]> getTopSellingProductsByStatus(OrderStatus status) {
        List<Object[]> topSellingProducts = orderDetailRepository.findTopSellingProductsByStatus(status);
        return topSellingProducts;
    }

    // lấy tất cả với điều kiện là isDelete
    public List<Object[]> getTopSellingProductsByIsDeleted(boolean isDelete) {
        return orderDetailRepository.findTopSellingProductsByIsDeleted(isDelete);
    }


    // Lấy danh sách các sản phẩm mới nhất với isDelete = false và đã được chuyển/giao
    public List<Product> getTopSellingProducts(int limit) {
        try {
            List<Product> allProducts = orderDetailRepository.findTopSellingProductsObj(false, OrderStatus.DELIVERED);
            limit = Math.min(limit, allProducts.size());
            return allProducts.stream()
                              .limit(limit)
                              .collect(Collectors.toList());
        } catch (Exception e) {
            // TODO: handle 
            return null;
        }
    }
}
