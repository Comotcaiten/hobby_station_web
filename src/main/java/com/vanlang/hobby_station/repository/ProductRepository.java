package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.Product;

// Sử dụng import java.util.Optional; thay vì import org.apache.el.stream.Optional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsDeleted(boolean isDeleted);

    Optional<Product> findByIdAndIsDeleted(Long id, boolean isDeleted);

    // Name
    List<Product> findByNameContaining(String name);
    List<Product> findByNameContainingAndIsDeleted(String name, boolean isDeleted);

    // Brand
    List<Product> findByBrandId(Long brandId);
    List<Product> findByBrandIdAndIsDeleted(Long brandId, boolean isDeleted);

    // Brand and Category
    List<Product> findByBrandIdAndCategoryIdAndIsDeleted(Long brandId, Long categoryId, boolean isDeleted);

    // Category
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByCategoryIdAndIsDeleted(Long categoryId, boolean isDeleted);
    
    // Price
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByPriceLessThan(Double price);
    List<Product> findByPriceEquals(Double price);

    List<Product> findByPriceGreaterThanAndIsDeleted(Double price, boolean isDeleted);
    List<Product> findByPriceLessThanAndIsDeleted(Double price, boolean isDeleted);
    List<Product> findByPriceEqualsAndIsDeleted(Double price, boolean isDeleted);

    // Date
    List<Product> findAllByOrderByCreatedAtDesc();
    List<Product> findAllByIsDeletedOrderByCreatedAtDesc(boolean isDeleted);
}
